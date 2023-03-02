package org.cubeville.cvstats.database;

import org.bukkit.entity.Player;
import org.cubeville.cvstats.CVStats;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class StatsDB extends SQLite {

	public String SQLCreateMetricEventsTable = "CREATE TABLE IF NOT EXISTS metric_events (" +
			"`metric_id` INTEGER PRIMARY KEY AUTO_INCREMENT, " +
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
		update(SQLCreateMetricEventsTable);
		update(SQLCreateMetricFieldsTable);
	}

	public void sendMetricEvent(String metric, Map<String, String> fields) {
		update(
			"INSERT INTO `metric_events` (timestamp, metric_name)"
					+ String.format("VALUES (%o, \"%s\", \"%s\"); ", System.currentTimeMillis(), metric)
		);
		StringBuilder query = new StringBuilder();
		query.append("INSERT INTO `metric_fields` (metric_id, field_name, field_value) VALUES ");
		for (String name : fields.keySet()) {
			query.append(String.format("(LAST_INSERT_ID(), \"%s\", \"%s\"),", name, fields.get(name)));
		}
		// replace last char with
		query.setCharAt(query.length() - 1, ';');
		update(query.toString());
	}
}

//SELECT
//	filtered.location,
//	COUNT(*) as `count`
//	FROM (
//	SELECT
//	metric_events.metric_id,
//	metric_events.metric_name,
//	group_concat(distinct case when metric_fields.field_name = 'location' then metric_fields.field_value end) `location`
//	FROM metric_events
//	INNER JOIN metric_fields ON metric_events.metric_id=metric_fields.metric_id
//	GROUP BY metric_events.metric_id
//	HAVING metric_name = "hub_portal"
//	) as filtered
//	GROUP BY filtered.location
//	ORDER BY `count` DESC
