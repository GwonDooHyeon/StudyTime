package jdbc;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class StudyBoardDTO {
	
	private String bNo, subject, content, nickName, userId, hit, good, bad, regDate, uptDate, replyNum, sNo;

}