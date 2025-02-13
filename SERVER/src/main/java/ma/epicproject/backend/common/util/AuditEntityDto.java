package ma.epicproject.backend.common.util;

import ma.epicproject.backend.common.bean.BaseDto;


public class AuditEntityDto extends BaseDto {

	/** Colonne */
	private String objectName;

	/** OldValue */
	private String data;

	/** UserId */
	private Long userId;

	/** Username */
	private String username;

	/** Type_action */
	private String actionType;

	/** ObjectId */
	private Long objectId;

	private String date;

	public AuditEntityDto() {

	}

	public AuditEntityDto(Long id) {
		super(id);
	}


	public String getObjectName() {
		return objectName;
	}

	public void setObjectName(String objectName) {
		this.objectName = objectName;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getActionType() {
		return actionType;
	}

	public void setActionType(String actionType) {
		this.actionType = actionType;
	}

	public Long getObjectId() {
		return objectId;
	}

	public void setObjectId(Long objectId) {
		this.objectId = objectId;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

}