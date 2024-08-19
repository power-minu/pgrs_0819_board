package org.example.day0819_board.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MemberRepositoryMysql implements MemberRepository {
    private MemberRepositoryMysql(){}
    private static MemberRepository instance = new MemberRepositoryMysql();
    public static MemberRepository getInstance() {
        return instance;
    }

    private Connection conn;
    private PreparedStatement ps;
    private ResultSet rs;

    @Override
    public int insert(MemberDTO member) throws SQLException {
        int result = 0;
        try {
            String sql = " INSERT INTO " +
                    " MEMBER_TB(USER_ID, USER_PW) VALUES(?,?) ";
            conn = DBUtil.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, member.getUserId());
            ps.setString(2, member.getUserPw());
            result = ps.executeUpdate();
        }catch (SQLException ex){
            System.out.println("insert error");
            throw ex;
        }finally {
            DBUtil.close(ps, conn);
        }
        return result;
    }

    @Override
    public MemberDTO exists(String id) throws SQLException {
        MemberDTO member = null;

        try {
            String sql = " SELECT * FROM MEMBER_TB WHERE USER_ID=?";
            conn = DBUtil.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, id);
            rs = ps.executeQuery();
            if(rs.next()){
                member = new MemberDTO(rs.getString("user_id"),rs.getString("user_pw"));
            }
        }catch (SQLException ex){
            System.out.println("error");
            throw ex;
        }finally {
            DBUtil.close(rs, ps, conn);
        }

        return member;
    }
}
