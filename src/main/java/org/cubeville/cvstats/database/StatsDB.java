package org.cubeville.cvstats.database;

import org.bukkit.entity.Player;
import org.cubeville.cvstats.leaderboards.Leaderboard;
import org.cubeville.cvstats.leaderboards.LeaderboardSortBy;
import org.cubeville.cvstats.leaderboards.LeaderboardValueFormat;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class StatsDB extends SQLite {

	public String SQLCreateMetricEventsTable = "CREATE TABLE IF NOT EXISTS metric_events (" +
			"`metric_id` INTEGER PRIMARY KEY AUTOINCREMENT, " +
			"`metric_name` varchar(200) NOT NULL, " +
			"`timestamp` BIGINT NOT NULL " +
			");";

	public String SQLCreateMetricFieldsTable = "CREATE TABLE IF NOT EXISTS metric_fields (" +
			"`metric_id` INTEGER NOT NULL," +
			"`field_name` varchar(200) NOT NULL," +
			"`field_value` varchar(200) NOT NULL" +
			");";

	public StatsDB(String fileName) {
		super(fileName);
	}

	public void load() {
		connect();
		update(SQLCreateMetricEventsTable);
		update(SQLCreateMetricFieldsTable);
	}

	public void sendMetricEvent(String metric, Map<String, String> fields) {
		batchUpdate(List.of(
			"BEGIN TRANSACTION;",
			"CREATE TEMPORARY TABLE IF NOT EXISTS insert_metric_fields (metric_id INTEGER, field_name varchar(200), field_value varchar(200));",
			"DELETE FROM insert_metric_fields;",
			addMetricFieldsQuery(fields),
			addMetricEventQuery(metric),
			"UPDATE insert_metric_fields SET metric_id=last_insert_rowid();",
			"INSERT INTO metric_fields (metric_id, field_name, field_value) SELECT * FROM insert_metric_fields;",
			"END TRANSACTION;"
		));
	}

	public void clearMetricEvent(String metric, Map<String, String> fields) {
		batchUpdate(List.of(
			"BEGIN TRANSACTION;",
			deleteMetricEventQuery(metric, fields),
			"DELETE FROM metric_fields WHERE metric_id NOT IN ( SELECT DISTINCT metric_id FROM metric_fields );",
			"END TRANSACTION;"
		));
	}

	private String deleteMetricEventQuery(String metric, Map<String, String> fields) {
		return "DELETE FROM metric_events\n" +
			String.format("WHERE metric_name = '%s'\n", metric) +
			getFiltersString("metric_events", fields) +
			";";
	}

	private String addMetricEventQuery(String metric) {
		return "INSERT INTO `metric_events` (timestamp, metric_name) "
			+ String.format("VALUES (%o, \"%s\");", System.currentTimeMillis(), metric);
	}

	private String addMetricFieldsQuery(Map<String, String> fields) {
		StringBuilder query = new StringBuilder();
		query.append("INSERT INTO `insert_metric_fields` (metric_id, field_name, field_value) VALUES ");
		for (String name : fields.keySet()) {
			query.append(String.format("(last_insert_rowid(), \"%s\", \"%s\"),", name, fields.get(name)));
		}
		// replace last char with
		query.setCharAt(query.length() - 1, ';');
		return query.toString();
	}

	public ResultSet fetchLeaderboard(Leaderboard leaderboard) {
		if (Objects.equals(leaderboard.value, "count")) {
			return fetchCountLeaderboard(leaderboard);
		}
		return fetchValueLeaderboard(leaderboard);
	}

	public ResultSet fetchCountLeaderboard(Leaderboard leaderboard) {
		String query =
			"SELECT\n" +
			"    metricTable.metric_id,\n" +
			"    metricTable.metric_name,\n" +
			"    fieldTable.field_value AS `key`,\n" +
			"    COUNT(fieldTable.field_value) AS `value`\n" +
			"FROM metric_events AS metricTable\n" +
			"JOIN metric_fields AS fieldTable\n" +
			"ON fieldTable.metric_id = metricTable.metric_id\n" +
			String.format("WHERE metricTable.metric_name = '%s'\n", leaderboard.metric) +
			String.format("AND fieldTable.field_name = '%s'\n", leaderboard.key) +
			getFiltersString("metricTable", leaderboard.getFilters()) +
			"GROUP BY `key`\n" +
			String.format("ORDER BY `value` %s\n", leaderboard.sortBy.name()) +
			String.format("LIMIT %d;", leaderboard.size);

		return getResult(query);
	}

	public ResultSet fetchValueLeaderboard(Leaderboard leaderboard) {
		String valueSort = getValueSort(leaderboard);
		String query =
			"SELECT\n" +
			"    metricTable.metric_id,\n" +
			"    metricTable.metric_name,\n" +
			"    keyTable.field_value AS `key`,\n" +
			String.format("    %s AS `value`\n", valueSort) +
			"FROM metric_events AS metricTable\n" +
			"JOIN metric_fields AS keyTable\n" +
			"ON metricTable.metric_id = keyTable.metric_id\n" +
			"JOIN metric_fields AS valueTable\n" +
			"ON keyTable.metric_id = valueTable.metric_id\n" +
			String.format("WHERE metricTable.metric_name = '%s'\n", leaderboard.metric) +
			String.format("AND keyTable.field_name = '%s'\n", leaderboard.key) +
			String.format("AND valueTable.field_name = '%s'\n", leaderboard.value) +
			getFiltersString("metricTable", leaderboard.getFilters()) +
			"GROUP BY `key`\n" +
			String.format("ORDER BY `value` %s\n", leaderboard.sortBy.name()) +
			String.format("LIMIT %d;", leaderboard.size);

		return getResult(query);
	}

	private String getValueSort(Leaderboard leaderboard) {
		String valueSort = "valueTable.field_value";
		if (leaderboard.valueFormat.equals(LeaderboardValueFormat.NUMBER) || leaderboard.valueFormat.equals(LeaderboardValueFormat.TIME_MILLI)) {
			valueSort = "CAST(" + valueSort + " AS int)";
		}

		if (leaderboard.sortBy.equals(LeaderboardSortBy.ASC)) {
			valueSort = "MIN(" + valueSort + ")";
		} else {
			valueSort = "MAX(" + valueSort + ")";
		}
		return valueSort;
	}

	private String getFiltersString(String tableName, Map<String, String> filters) {
		StringBuilder result = new StringBuilder();
		for (String key : filters.keySet()) {
			String currentFilter = "" +
				"AND EXISTS (\n" +
				"    SELECT * FROM metric_fields as filters\n" +
				String.format("    WHERE filters.metric_id = %s.metric_id\n", tableName) +
				String.format("    AND filters.field_name = '%s'\n", key) +
				String.format("    AND filters.field_value = '%s'\n", filters.get(key)) +
				")\n";
			result.append(currentFilter);
		}
		return result.toString();
	}
}
