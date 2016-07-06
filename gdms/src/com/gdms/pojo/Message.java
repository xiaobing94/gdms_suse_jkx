package com.gdms.pojo;

import java.util.Date;

public class Message {
    private Integer id;

    private Integer recvId;

    private Integer fromId;

    private Date dateline;

    private Integer status;

    private String note;
    
    private User recver;
    
    private User fromer;
    
    public static final int READ = 1;
    
    public static final int UNREAD = 0;

    public User getRecver() {
		return recver;
	}

	public void setRecver(User recver) {
		this.recver = recver;
	}

	public User getFromer() {
		return fromer;
	}

	public void setFromer(User fromer) {
		this.fromer = fromer;
	}

	public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getRecvId() {
        return recvId;
    }

    public void setRecvId(Integer recvId) {
        this.recvId = recvId;
    }

    public Integer getFromId() {
        return fromId;
    }

    public void setFromId(Integer fromId) {
        this.fromId = fromId;
    }

    public Date getDateline() {
        return dateline;
    }

    public void setDateline(Date dateline) {
        this.dateline = dateline;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note == null ? null : note.trim();
    }
    
//    public void setRecverAndFromer(User)
}