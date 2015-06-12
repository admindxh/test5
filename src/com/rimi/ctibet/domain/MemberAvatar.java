package com.rimi.ctibet.domain;

public class MemberAvatar {
	private int id;
	private int avaliable;
	private String code;
	private String memberCode;
	private String avatars;
	private int useSystem;
	private String systemAvatar;
	private String folderId;

	public MemberAvatar() {
	}

	public MemberAvatar(int id, int avaliable, String code, String memberCode,
			String avatars, int useSystem, String systemAvatar,
			String folderId) {
		this.id=id;
		this.avaliable=avaliable;
		this.code=code;
		this.memberCode=memberCode;
		this.avatars=avatars;
		this.useSystem=useSystem;
		this.systemAvatar=systemAvatar;
		this.folderId=folderId;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getAvaliable() {
		return avaliable;
	}

	public void setAvaliable(int avaliable) {
		this.avaliable = avaliable;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMemberCode() {
		return memberCode;
	}

	public void setMemberCode(String memberCode) {
		this.memberCode = memberCode;
	}

	public String getAvatars() {
		return avatars;
	}

	public void setAvatars(String avatars) {
		this.avatars = avatars;
	}

	public int getUseSystem() {
		return useSystem;
	}

	public void setUseSystem(int useSystem) {
		this.useSystem = useSystem;
	}

	public String getSystemAvatar() {
		return systemAvatar;
	}

	public void setSystemAvatar(String systemAvatar) {
		this.systemAvatar = systemAvatar;
	}

	public String getFolderId() {
		return folderId;
	}

	public void setFolderId(String folderId) {
		this.folderId = folderId;
	}
	
	
}
