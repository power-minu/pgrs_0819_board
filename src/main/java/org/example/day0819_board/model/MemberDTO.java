package org.example.day0819_board.model;

public class MemberDTO {
    private String userId;
    private String userPw;

    public MemberDTO() {
    }

    public MemberDTO(String userId, String userPw) {
        this.userId = userId;
        this.userPw = userPw;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserPw() {
        return userPw;
    }

    public void setUserPw(String userPw) {
        this.userPw = userPw;
    }

    @Override
    public String toString() {
        return "MemberDTO{" +
                "userId='" + userId + '\'' +
                ", userPw='" + userPw + '\'' +
                '}';
    }
}
