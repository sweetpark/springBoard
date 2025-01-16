//package project.board.domain.repository.boardRepository;
//
//import com.fasterxml.jackson.core.JsonProcessingException;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.springframework.jdbc.datasource.DataSourceUtils;
//import org.springframework.jdbc.support.JdbcUtils;
//import org.springframework.jdbc.support.SQLErrorCodeSQLExceptionTranslator;
//import org.springframework.jdbc.support.SQLExceptionTranslator;
//import project.board.domain.entity.Board;
//import project.board.domain.entity.Member;
//import project.board.web.dto.UploadFile;
//
//import javax.sql.DataSource;
//import java.sql.*;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.NoSuchElementException;
//
//public class JDBCBoardRepository implements BoardRepository{
//
//
//    private final DataSource dataSource;
//    private final SQLExceptionTranslator excep;
//    ObjectMapper objectMapper = new ObjectMapper();
//
//    public JDBCBoardRepository(DataSource dataSource){
//        this.dataSource = dataSource;
//        this.excep = new SQLErrorCodeSQLExceptionTranslator(dataSource);
//    }
//
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
//    public void save(Board board) {
//        Connection con = null;
//        PreparedStatement pstmt= null;
//        String sql = "insert into board(title, body, memberInfo, imageFiles, attachFile) values(?,?,?,?,?) ";
//        try{
//            con = getConnection();
//            pstmt = con.prepareStatement(sql);
//
//            pstmt.setString(1, board.getTitle());
//            pstmt.setString(2, board.getBody());
//
//            String memberJson = objectMapper.writeValueAsString(board.getMemberInfo());
//            String imageFilesJson = objectMapper.writeValueAsString(board.getImageFiles());
//            String attachFileJson = objectMapper.writeValueAsString(board.getAttachFile());
//
//            pstmt.setString(3, memberJson);
//            pstmt.setString(4, imageFilesJson);
//            pstmt.setString(5, attachFileJson);
//            pstmt.executeUpdate();
//        }catch (SQLException e){
//            throw excep.translate("save", sql,e);
//        }catch(JsonProcessingException e){
//          throw new RuntimeException(e);
//        } finally{
//            close(con,pstmt,null);
//        }
//
//    }
//
//    @Override
//    public void delete(Long id) {
//        Connection con = null;
//        PreparedStatement pstmt = null;
//        String sql = "delete from board where id=?";
//
//        try{
//            con = getConnection();
//            pstmt = con.prepareStatement(sql);
//            pstmt.setLong(1,id);
//            pstmt.executeUpdate();
//        }catch (SQLException e){
//            throw excep.translate("delete", sql,e);
//        }finally{
//            close(con,pstmt,null);
//        }
//    }
//
//    @Override
//    public Board findByBoard(Long id) {
//        Connection con = null;
//        PreparedStatement pstmt = null;
//        ResultSet rs = null;
//        String sql = "select * from board where id=?";
//
//        try{
//            con = getConnection();
//            pstmt = con.prepareStatement(sql);
//            pstmt.setLong(1,id);
//            rs = pstmt.executeQuery();
//
//            if(rs.next()){
//                Board findBoard = new Board();
//                findBoard.setId(rs.getLong("id"));
//                findBoard.setTitle(rs.getString("title"));
//                findBoard.setBody(rs.getString("body"));
//
//                try{
//                    String memberJson = rs.getString("memberInfo");
//                    Member memberInfo = objectMapper.readValue(memberJson, Member.class);
//                    findBoard.setMemberInfo(memberInfo);
//                }catch(JsonProcessingException e){
//                    throw new RuntimeException("memberInfo json 변환 실패");
//                }
//
//
//                try {
//                    String imageFilesJson = rs.getString("imageFiles");
//                    List<UploadFile> imageFiles = objectMapper.readValue(imageFilesJson,
//                            objectMapper.getTypeFactory().constructCollectionType(List.class, UploadFile.class));
//                    findBoard.setImageFiles(imageFiles);
//                }catch(JsonProcessingException e){
//                    throw new RuntimeException(e);
//                }
//
//                try{
//                    String attachFileJson = rs.getString("attachFile");
//                    UploadFile attachFile = objectMapper.readValue(attachFileJson, UploadFile.class);
//                    findBoard.setAttachFile(attachFile);
//                }catch(JsonProcessingException e){
//                    throw new RuntimeException(e);
//                }
//
//                return findBoard;
//            }else{
//                throw new NoSuchElementException("board not found");
//            }
//        }catch (SQLException e){
//            throw excep.translate("findByBoard", sql,e);
//        }finally{
//            close(con,pstmt,rs);
//        }
//    }
//
//    //service 코드
//    @Override
//    public List<Board> findBoardsByMember(String loginId) {
//        List<Board> memberBoards = new ArrayList<>();
//        List<Board> boards = new ArrayList<>();
//        boards = findAll();
//        for (Board board : boards) {
//            if (board.getMemberInfo().getLoginId().equals(loginId)){
//                memberBoards.add(board);
//            }
//        }
//
//        return memberBoards;
//    }
//
//    @Override
//    public List<Board> findAll() {
//        List<Board> boards = new ArrayList<>();
//        Connection con = null;
//        PreparedStatement pstmt =null;
//        ResultSet rs = null;
//
//        String sql = "select * from board";
//
//        try{
//            con = getConnection();
//            pstmt = con.prepareStatement(sql);
//            rs = pstmt.executeQuery();
//
//            while(rs.next()){
//                Board findBoard = new Board();
//                findBoard.setId(rs.getLong("id"));
//                findBoard.setTitle(rs.getString("title"));
//                findBoard.setBody(rs.getString("body"));
//
//                String memberJson = rs.getString("memberInfo");
//                Member memberInfo = objectMapper.readValue(memberJson, Member.class);
//                findBoard.setMemberInfo(memberInfo);
//
//
//                String imageFilesJson = rs.getString("imageFiles");
//                List<UploadFile> imageFiles = objectMapper.readValue(imageFilesJson,
//                        objectMapper.getTypeFactory().constructCollectionType(List.class, UploadFile.class));
//                findBoard.setImageFiles(imageFiles);
//
//
//                String attachFileJson = rs.getString("attachFile");
//                UploadFile attachFile = objectMapper.readValue(attachFileJson, UploadFile.class);
//                findBoard.setAttachFile(attachFile);
//
//                boards.add(findBoard);
//            }
//        }catch(SQLException e){
//            throw excep.translate("findAll", sql,e);
//        }catch(JsonProcessingException e){
//            throw new RuntimeException(e);
//        }finally{
//            close(con,pstmt,rs);
//        }
//
//        return boards;
//    }
//
//    @Override
//    public void updateBoard(Long id, String title, UploadFile attachFile, List<UploadFile> images,String body) {
//        String sql = "update board set title=?, body=?, attachFile=?, imageFiles=? where id=?";
//        Connection con = null;
//        PreparedStatement pstmt = null;
//
//        try{
//            con = getConnection();
//            pstmt =con.prepareStatement(sql);
//
//            pstmt.setString(1, title);
//            pstmt.setString(2,body);
//            pstmt.setString(3, objectMapper.writeValueAsString(attachFile));
//            pstmt.setString(4, objectMapper.writeValueAsString(images));
//            pstmt.setLong(5,id);
//
//            pstmt.executeUpdate();
//        }catch(SQLException e){
//            throw excep.translate("update",sql,e);
//        }catch(JsonProcessingException e){
//            throw new RuntimeException(e);
//        }finally{
//            close(con,pstmt,null);
//        }
//
//    }
//
//    @Override
//    public void clear() {
//        Connection con = null;
//        PreparedStatement pstmt = null;
//        String sql = "truncate table board";
//
//        try{
//            con = getConnection();
//            pstmt = con.prepareStatement(sql);
//            pstmt.executeUpdate();
//        }catch (SQLException e){
//            throw excep.translate("clear", sql,e);
//        }finally{
//            close(con,pstmt,null);
//        }
//    }
//}
