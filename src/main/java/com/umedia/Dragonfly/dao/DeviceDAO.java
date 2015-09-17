package com.umedia.Dragonfly.dao;

public interface DeviceDAO {
	boolean addDevice(String deviceid, String token);

	boolean updateDevice(String deviceid, String token);

	boolean deleteDevice(String deviceid);

	String findDevice(String deviceid);

}
