package org.example.day0819_board.model;

import java.sql.SQLException;

public interface MemberRepository {
    int insert(MemberDTO member) throws SQLException;
    MemberDTO exists(String id) throws SQLException;
}
