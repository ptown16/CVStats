package org.cubeville.cvstats.database;

import org.bukkit.entity.Player;
import org.cubeville.cvstats.CVStats;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class StatsDB extends MySQLDatabase {

	public String SQLCreateMetricEventsTable = "CREATE TABLE IF NOT EXISTS metric_events (" +
			"`metric_id` INTEGER PRIMARY KEY AUTO_INCREMENT, " +
			"`player` varchar(36) NOT NULL, " +
			"`metric_name` varchar(200) NOT NULL, " +
			"`timestamp` BIGINT NOT NULL " +
			");";

	public String SQLCreateMetricFieldsTable = "CREATE TABLE IF NOT EXISTS metric_fields (" +
			"`metric_id` INTEGER NOT NULL," +
			"`field_name` varchar(200) NOT NULL," +
			"`field_value` varchar(200) NOT NULL" +
			");";


	public StatsDB(String hostname, int port, String database, String username, String password) {
		super(hostname, port, database, username, password);
	}

	public void load() {
		setupPool();
		update(SQLCreateMetricEventsTable);
		update(SQLCreateMetricFieldsTable);
	}

	public void sendMetricEvent(String metric, Player player, Map<String, String> fields) {
		update(
			"INSERT INTO `metric_events` (timestamp, player, metric_name)"
					+ String.format("VALUES (%o, \"%s\", \"%s\"); ", System.currentTimeMillis(), player.getUniqueId(), metric)
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
