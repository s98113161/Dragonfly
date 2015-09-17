package com.umedia.Dragonfly.dao;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;

public class DeviceDAOImpl implements DeviceDAO{
	JdbcTemplate jdbcTemplate ;
	
	public DeviceDAOImpl(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	public JdbcTemplate getTempleate() {
		return jdbcTemplate;
	}
	@Override
	public boolean addDevice(String deviceid, String token) {
		String sql=" INSERT INTO devices(deviceId, token) VALUES (?, ?)";
		jdbcTemplate.update(sql,deviceid,token);
		return true;

	}
	@Override
	public boolean updateDevice(String deviceid, String token ) {
		String sql=" UPDATE devices SET token=? WHERE deviceId=?";
		jdbcTemplate.update(sql,token,deviceid);
		return true;
	}

	@Override
	public boolean deleteDevice(String deviceid) {
		String sql="DELETE from devices WHERE deviceId=?";
		jdbcTemplate.update(sql,deviceid);
		return true;
	}

	@Override
	public String findDevice(String deviceid) {
		String sql = "SELECT token FROM devices WHERE deviceId = ?";

		
	String name = jdbcTemplate.queryForObject(
		sql, String.class, deviceid);
	
	return name;
	}

}
