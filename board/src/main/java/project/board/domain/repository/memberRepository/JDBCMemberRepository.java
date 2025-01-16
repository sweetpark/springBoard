//package project.board.domain.repository.memberRepository;
//
//import com.fasterxml.jackson.core.JsonProcessingException;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.springframework.jdbc.datasource.DataSourceUtils;
//import org.springframework.jdbc.support.JdbcUtils;
//import org.springframework.jdbc.support.SQLErrorCodeSQLExceptionTranslator;
//import org.springframework.jdbc.support.SQLExceptionTranslator;
//import project.board.common.auth.enc.AES256Enc;
//import project.board.domain.entity.Member;
//
//import javax.sql.DataSource;
//import java.sql.*;
//import java.util.ArrayList;
//import java.util.List;
//
//
//
//public class JDBCMemberRepository implements MemberRepository{
//
//
//    private final DataSource dataSource;
//    private final SQLExceptionTranslator excep;
//
//    public JDBCMemberRepository(DataSource dataSource){
//        this.dataSource =dataSource;
//        this.excep = new SQLErrorCodeSQLExceptionTranslator(dataSource);
//    }
//
//    private Connection getConnection() throws SQLException{
//        Connection con = DataSourceUtils.getConnection(dataSource);
//        return con;
//    }
//
//    private void close(Connection con, Statement stmt, ResultSet rs){
//        JdbcUtils.closeResultSet(rs);
//        JdbcUtils.closeStatement(stmt);
//        DataSourceUtils.releaseConnection(con, dataSource);
//    }
//
//    @Override
//    public void save(Member member) {
//        Connection con = null;
//        PreparedStatement pstmt = null;
//        String sql = "insert into member(loginId, name, passwd, address) values(?,?,?,?)";
//
//        try{
//            con = getConnection();
//            pstmt =con.prepareStatement(sql);
//            pstmt.setString(1, member.getLoginId());
//            pstmt.setString(2, member.getName());
//            pstmt.setString(3, AES256Enc.encrypt(member.getPasswd()));
//            pstmt.setString(4, member.getAddress());
//            pstmt.executeUpdate();
//        }catch(SQLException e){
//            throw excep.translate("save", sql,e);
//        }finally{
//            close(con,pstmt,null);
//        }
//
//    }
//
//    @Override
//    public void delete(Long id) {
//        Connection con = null;
//        PreparedStatement pstmt = null;
//        String sql = "delete from member where id=?";
//
//        try{
//            con = getConnection();
//            pstmt = con.prepareStatement(sql);
//            pstmt.setLong(1, id);
//            pstmt.executeUpdate();
//        }catch(SQLException e){
//            throw excep.translate("delete", sql, e);
//        }finally{
//            close(con,pstmt,null);
//        }
//    }
//
//    @Override
//    public Member findById(Long id) {
//        Connection con = null;
//        PreparedStatement pstmt = null;
//        ResultSet rs = null;
//        String sql = "select * from member where id =?";
//
//        try{
//            con =getConnection();
//            pstmt = con.prepareStatement(sql);
//            pstmt.setLong(1, id);
//
//            rs = pstmt.executeQuery();
//
//            if(rs.next()){
//                Member findMember = new Member();
//                findMember.setId(rs.getLong("id"));
//                findMember.setLoginId(rs.getString("loginId"));
//                findMember.setName(rs.getString("name"));
//                findMember.setPasswd(rs.getString("passwd"));
//                findMember.setAddress(rs.getString("address"));
//
//                return findMember;
//            }else{
//                return null;
//            }
//
//        }catch(SQLException e){
//            throw excep.translate("findByLoginId", sql, e);
//        }finally{
//            close(con, pstmt, rs);
//        }
//
//    }
//
//    @Override
//    public Member findByLoginId(String loginId) {
//        Connection con = null;
//        PreparedStatement pstmt =null;
//        ResultSet rs = null;
//        String sql = "select * from member where loginId = ?";
//        try{
//            con =getConnection();
//            pstmt = con.prepareStatement(sql);
//            pstmt.setString(1,loginId);
//
//            rs = pstmt.executeQuery();
//
//            if(rs.next()){
//                Member findMember = new Member();
//                findMember.setId(rs.getLong("id"));
//                findMember.setLoginId(rs.getString("loginId"));
//                findMember.setName(rs.getString("name"));
//                findMember.setPasswd(rs.getString("passwd"));
//                findMember.setAddress(rs.getString("address"));
//                findMember.setAddress(rs.getString("phone"));
//                findMember.setAddress(rs.getString("email"));
//
//                return findMember;
//            }else{
//                return null;
//            }
//
//        }catch(SQLException e){
//            throw excep.translate("findByLoginId", sql, e);
//        }finally{
//            close(con, pstmt, rs);
//        }
//    }
//
//    @Override
//    public List<Member> findAll() {
//        List<Member> members = new ArrayList<>();
//        Connection con = null;
//        PreparedStatement pstmt = null;
//        ResultSet rs = null;
//        String sql = "select * from member";
//
//
//        try{
//            con = getConnection();
//            pstmt = con.prepareStatement(sql);
//            rs = pstmt.executeQuery();
//
//            while(rs.next()){
//                Member findMember = new Member();
//                findMember.setId(rs.getLong("id"));
//                findMember.setLoginId(rs.getString("loginId"));
//                findMember.setName(rs.getString("name"));
//                findMember.setPasswd(rs.getString("passwd"));
//                findMember.setAddress(rs.getString("address"));
//                findMember.setAddress(rs.getString("phone"));
//                findMember.setAddress(rs.getString("email"));
//
//                members.add(findMember);
//            }
//        }catch(SQLException e){
//            throw excep.translate("findAll", sql, e);
//        }finally{
//            close(con,pstmt, rs);
//        }
//
//        return members;
//    }
//
//    @Override
//    public void updateMember(Long id,Member member) {
//        Connection con = null;
//        PreparedStatement pstmt = null;
//        String sql = "update member set name =?, passwd =?, address = ? where loginId =?";
//        String boardSql = "update board set memberInfo =? where memberInfo like ?";
//        try{
//            con = getConnection();
//            pstmt =con.prepareStatement(sql);
//            pstmt.setString(1, member.getName());
//            pstmt.setString(2, AES256Enc.encrypt(member.getPasswd()));
//            pstmt.setString(3, member.getAddress());
//            pstmt.setString(4, member.getLoginId());
//
//            pstmt.executeUpdate();
//            pstmt.close();
//
//            ObjectMapper objectMapper = new ObjectMapper();
//            String memberInfoJson = objectMapper.writeValueAsString(member);
//
//            pstmt = con.prepareStatement(boardSql);
//            pstmt.setString(1, memberInfoJson);
//            pstmt.setString(2, "%" + member.getLoginId() + "%");
//            pstmt.executeUpdate();
//
//
//        }catch (SQLException e){
//            throw excep.translate("update", sql, e);
//        }catch(JsonProcessingException e){
//          throw new RuntimeException(e);
//        } finally{
//            close(con,pstmt,null);
//        }
//
//
//    }
//
//    @Override
//    public void clear() {
//        Connection con = null;
//        PreparedStatement pstmt = null;
//        String sql = "truncate table member";
//
//        try{
//            con = getConnection();
//            pstmt = con.prepareStatement(sql);
//            pstmt.executeUpdate();
//
//        }catch (SQLException e){
//            throw excep.translate("clear", sql, e);
//        }finally{
//            close(con, pstmt, null);
//        }
//    }
//}
