/*
--------------------------------------------------------
최초작성자 : 최혜원(wone8115@uos.ac.kr)
최초작성일 : 2023/02/15

버전 기록 : ver1(시작 23/02/15)
--------------------------------------------------------
*/

package jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.*;


import javax.naming.NamingException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import util.ConnectionPool;

public class StudyJoinDAO {

	private static PreparedStatement pstmt;
	private static String sql;
	private static Connection conn;
	private static ResultSet rs;

	public StudyJoinDAO() {
		try {conn = ConnectionPool.get();} catch (NamingException | SQLException e) {e.printStackTrace();}
	}
	
	
	
		//스터디참여(지원)
		public static boolean studyJoin(String userId, String sNo, String approve){
			sql = "INSERT INTO studyjoin(userid,sNo,approve) VALUES(?,?,?)";
			try {
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, userId);
				pstmt.setString(2, sNo);
				pstmt.setString(3, approve);
				if(pstmt.executeUpdate()==1) return true;
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				 if (pstmt != null) try { pstmt.close(); } catch(Exception e) {e.printStackTrace();}
		         if (conn != null) try { conn.close(); } catch(Exception e) {e.printStackTrace();}
			}
			return false;
		}



	// 특정 스터디 가입 신청 목록 보기(혜원)
	public static String getList(String sNo){
		String SQL = "SELECT sj.sNo u.userid, u.nickname, u.name, u.gender FROM studyJoin sj " +
				"JOIN user u ON sj.userid = u.userid " +
				"WHERE sj.sNo = ? " +
				"ORDER BY sj.regDate DESC";
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		JSONArray userList = new JSONArray();

		try {
			conn = ConnectionPool.get();
			pstmt = conn.prepareStatement(SQL);

			pstmt.setString(1, sNo);

			rs = pstmt.executeQuery();

			while(rs.next()) {
				JSONObject obj = new JSONObject();
				obj.put("sNo", rs.getString(1));
				obj.put("userid", rs.getString(2));
				obj.put("nickname", rs.getString(3));
				obj.put("name", rs.getString(4));
				obj.put("gender", rs.getString(5));

				userList.add(obj);

			}

		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if(rs!=null) rs.close();
				if(pstmt!= null) pstmt.close();
				if(conn!=null) conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return userList.toJSONString();
	}
	
//	스터디 그룹원 목록 메서드(도영)
	public static ArrayList<StudyJoinDTO> getAllList(String sNo){

		ArrayList<StudyJoinDTO> StudyJoins = new ArrayList<StudyJoinDTO>();

		try {
			sql = " SELECT * FROM studyjoin WHERE sNo=? AND approve='승인' ";

			try {
				conn = ConnectionPool.get();
			} catch (NamingException e) {
				e.printStackTrace();
			}

			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, sNo);
			
			rs = pstmt.executeQuery();



			while(rs.next()) {
				StudyJoins.add(new StudyJoinDTO(rs.getString(1),
									rs.getString(2),
									rs.getString(3),
									rs.getString(4),
									rs.getString(5)));
			}

				return StudyJoins;

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if(pstmt != null)	pstmt.close();
				if(conn != null)	conn.close();
				if(rs != null)	rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return StudyJoins;

		}
	
//	스터디 그룹원 목록 메서드(도영)
	public static ArrayList<StudyJoinDTO> getAllList2(String sNo){

		ArrayList<StudyJoinDTO> StudyJoins = new ArrayList<StudyJoinDTO>();

		try {
			sql = " SELECT * FROM studyjoin WHERE sNo=? AND approve='거절' ";

			try {
				conn = ConnectionPool.get();
			} catch (NamingException e) {
				e.printStackTrace();
			}

			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, sNo);
			
			rs = pstmt.executeQuery();



			while(rs.next()) {
				StudyJoins.add(new StudyJoinDTO(rs.getString(1),
									rs.getString(2),
									rs.getString(3),
									rs.getString(4),
									rs.getString(5)));
			}

				return StudyJoins;

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if(pstmt != null)	pstmt.close();
				if(conn != null)	conn.close();
				if(rs != null)	rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return StudyJoins;

		}

//	스터디 그룹원 추방 메서드(도영)
	public static boolean delete(String userId, String sNo){
		
	try {
		sql = "UPDATE studyjoin SET approve='거절' where userid=? AND sNo=? ";

		try {
			conn = ConnectionPool.get();
		} catch (NamingException e) {
			e.printStackTrace();
		}
		
		pstmt = conn.prepareStatement(sql);

		pstmt.setString(1, userId);
		pstmt.setString(2, sNo);

		int result = pstmt.executeUpdate();
		if (result == 1) {
			return true;
		}
	} catch (SQLException e) {
		e.printStackTrace();
	} finally {
		
		try {
			if(pstmt != null)	pstmt.close();
			if(conn != null)	conn.close();
			if(rs != null)	rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	return false;

}
	
//	스터디 그룹원 재가입 메서드(도영)
	public static boolean reRegister(String userId, String sNo){
		
	try {
		sql = "UPDATE studyjoin SET approve='승인' where userid=? AND sNo=? ";

		try {
			conn = ConnectionPool.get();
		} catch (NamingException e) {
			e.printStackTrace();
		}
		
		pstmt = conn.prepareStatement(sql);

		pstmt.setString(1, userId);
		pstmt.setString(2, sNo);

		int result = pstmt.executeUpdate();
		if (result == 1) {
			return true;
		}
	} catch (SQLException e) {
		e.printStackTrace();
	} finally {
		
		try {
			if(pstmt != null)	pstmt.close();
			if(conn != null)	conn.close();
			if(rs != null)	rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	return false;

}

	// 스터디 가입 거절(혜원)
	public static boolean reject(String sNo, String userId) {


		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		boolean result = false;

		try {
			String sql = "UPDATE studyJoin SET approve = 1 WHERE sNo = ? and userid = ?";

			conn = ConnectionPool.get();
			pstmt = conn.prepareStatement(sql);

			pstmt.setString(1, sNo);
			pstmt.setString(2, userId);

			result = pstmt.execute();

		}catch (Exception e) {
			e.printStackTrace();

		}finally {
			try {
				if(pstmt != null) pstmt.close();
				if(conn != null) conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return result;
	}

//	그룹장확인 메서드(도영)
	public static boolean checkManager(String userId){


		StudyJoinDTO studyjoin = null;
		try {
			sql = "SELECT from studyjoin where userid=? ";

			try {
				conn = ConnectionPool.get();
			} catch (NamingException e) {
				e.printStackTrace();
			}

			pstmt = conn.prepareStatement(sql);

			pstmt.setString(1, userId);

			rs = pstmt.executeQuery();

			if (rs.next()) {
				studyjoin = new StudyJoinDTO(rs.getString("sjNo"),
						rs.getString("userid"),
						rs.getString("sNo"),
						rs.getString("approve"),
						rs.getString("regDate"));
			}

			if(studyjoin.getApprove().equals("3")) {
				return true;
			}else {
				return false;
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {

			try {
				if(pstmt != null)	pstmt.close();
				if(conn != null)	conn.close();
				if(rs != null)	rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}

		}

		return false;

	}

		//myStudy(소영)
		public static ArrayList<StudyJoinDTO>  myStudyOne(String userId) {
			String sql =  "SELECT * FROM studyJoin WHERE userid=? ";
			try {
				conn = ConnectionPool.get();
				rs = pstmt.executeQuery();
				
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, userId);

				ArrayList<StudyJoinDTO> studyJoins = new ArrayList<StudyJoinDTO>();
				while (rs.next()) {
					studyJoins.add(new StudyJoinDTO(rs.getString(1),
							rs.getString(2),
							rs.getString(3),
							rs.getString(4),
							rs.getString(5)));
					
					return studyJoins;
				}
			} catch (NamingException | SQLException e) {
				e.printStackTrace();
			} finally {
				try {
				if(pstmt != null)	pstmt.close();
				if(conn != null)	conn.close();
				if(rs != null)	rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			return null;
		}
		
		// 스터디 탈퇴(소영)
			public static boolean studyDelete(String userId, String sNo) {

				try {
					String sql = "DELETE FROM studyJoin WHERE userId = ? AND sNo = ? ";
					
					try {
						conn = ConnectionPool.get();
					} catch (NamingException e) {
						e.printStackTrace();
					}
						pstmt = conn.prepareStatement(sql);
						pstmt.setString(1, userId);
						pstmt.setString(2, sNo);

					int result = pstmt.executeUpdate();
					if (result == 1) {
						return true;
					}
				} catch (SQLException e) {
					e.printStackTrace();
				} finally {
					try {
						if(pstmt!= null) pstmt.close();
						if(conn!=null) conn.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
				return false;
			}



}
