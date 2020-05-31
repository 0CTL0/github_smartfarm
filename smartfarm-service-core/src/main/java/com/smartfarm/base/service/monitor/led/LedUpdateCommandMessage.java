package com.smartfarm.base.service.monitor.led;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class LedUpdateCommandMessage {
	/** 网络标识 **/
	public static final byte[] PACKAGE_TAG = new byte[] { (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF,
			(byte) 0xFF, (byte) 0xFF, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00 };
	/** 标志尾 **/
	public static final byte PACKAGE_TAG_TAIL = (byte) 0xA5;

	private String content;
	private int area;

	private byte start;
	private byte ver;
	private byte[] address;
	private byte cmd;
	private byte[] ident;
	private byte[] frame;

	public LedUpdateCommandMessage(String content, int area) {
		this.content = content;
		this.area = area;

		start = (byte) 0x78;
		ver = (byte) 0x34;
		address = new byte[] { (byte) 0x01, (byte) 0x00 };
		cmd = (byte) 0x29;
		ident = new byte[] { (byte) 0xBC, (byte) 0xFD, (byte) 0x00, (byte) 0x00 };
		frame = new byte[] { (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00 };
	}

	public byte[] toByte() {
		ByteArrayOutputStream out = new ByteArrayOutputStream();

		try {

			out.write(PACKAGE_TAG);
			// Data
			out.write(this.getHeaderData());
			out.write(this.getContentData());

			// Check
			out.write(this.getCheck(this.getHeaderData(), this.getContentData()));

			out.write(PACKAGE_TAG_TAIL);

		} catch (IOException e) {
			e.printStackTrace();
		}

		return out.toByteArray();
	}

	private byte[] getHeaderData() {
		ByteArrayOutputStream out = new ByteArrayOutputStream();

		try {
			out.write(this.start);

			out.write(this.ver);
			out.write(this.address);
			out.write(this.cmd);
			out.write(this.ident);
			out.write(this.frame);

		} catch (IOException e) {
			e.printStackTrace();
		}

		return out.toByteArray();
	}

	private byte[] getContentData() {
		// len 2byte

		// data nbyte
		// 字符分区ID 2byte （1）
		// 编码方式 1byte （1）0=unicode编码 | 1=gb2312 编码
		// 显示方式 1byte （2）0= 保存数据模式 | 2= 立即显示模式
		// 字符串索引 1byte （0） 0
		// 颜色 1byte （1）1 = 红色| 2 = 绿色| 3 = 黄色| 4 = 蓝色| 5 = 紫色| 6 = 青色| 7 = 白色
		// 长度 2byte N*2
		// 字符串 Nbyte

		// check 2byte //不包含

		ByteArrayOutputStream out = new ByteArrayOutputStream();

		try {
			byte[] content = HexUtils.encodeCNByte(this.content);

			// 长度
			int dataLen = 8 + content.length;

			out.write(HexUtils.intToByteArray(dataLen));

			// out.write((byte) 0x01); //分区
			// out.write((byte) 0x00);
			out.write(HexUtils.intToByteArray(this.area));

			out.write((byte) 0x01); // 编码

			out.write((byte) 0x02); // 显示方式

			out.write((byte) 0x06); // 字符串索引

			out.write((byte) 0x01); // 颜色

			int contentLend = this.content.length(); // 内容长度
			out.write(HexUtils.intToByteArray(contentLend * 2));

			out.write(content); // 内容

		} catch (IOException e) {
			e.printStackTrace();
		}

		return out.toByteArray();
	}

	private byte[] getCheck(byte[] header, byte[] content) {

		ByteArrayOutputStream out = new ByteArrayOutputStream();

		try {

			out.write(header); // 分区
			out.write(content);

			byte[] a = out.toByteArray();

			return CRC16Util.getCrc16(a);

		} catch (IOException e) {
			e.printStackTrace();
		}

		return null;
	}
}
