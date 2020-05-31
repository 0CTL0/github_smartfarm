package com.smartfarm.base.service.monitor.led;
public class CRC16Util {
	public static byte[] appendCrc16(String... strings) {
		byte[] data = new byte[] {};
		for (int i = 0; i < strings.length; i++) {
			int x = Integer.parseInt(strings[i], 16);
			byte n = (byte) x;
			byte[] buffer = new byte[data.length + 1];
			byte[] aa = { n };
			System.arraycopy(data, 0, buffer, 0, data.length);
			System.arraycopy(aa, 0, buffer, data.length, aa.length);
			data = buffer;
		}
		return appendCrc16(data);
	}

	public static byte[] appendCrc16(byte[] aa) {
		byte[] bb = getCrc16(aa);
		byte[] cc = new byte[aa.length + bb.length];
		System.arraycopy(aa, 0, cc, 0, aa.length);
		System.arraycopy(bb, 0, cc, aa.length, bb.length);
		return cc;
	}
	public static byte[] getCrc16(byte[] arr_buff) {
		int len = arr_buff.length;

		int crc = 0xFFFF;
		int i, j;
		for (i = 0; i < len; i++) {
			crc = ((crc & 0xFF00) | (crc & 0x00FF) ^ (arr_buff[i] & 0xFF));
			for (j = 0; j < 8; j++) {
				if ((crc & 0x0001) > 0) {
					crc = crc >> 1;
					crc = crc ^ 0xA001;
				} else
					crc = crc >> 1;
			}
		}
		return intToBytes(crc);
	}
	private static byte[] intToBytes(int value) {
		byte[] src = new byte[2];
		src[1] = (byte) ((value >> 8) & 0xFF);
		src[0] = (byte) (value & 0xFF);
		return src;
	}
}
