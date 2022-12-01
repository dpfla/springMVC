package com.leeyerim.springBoard.board.vo;

import java.net.URLEncoder;
import java.sql.Date;

import org.springframework.stereotype.Component;

@Component("imageVO")
public class ImageVO {
	private int imageFileNo;
	private int articleNo;
	private String imageFileName;
	private Date imgDate;
	
	
	public int getImageFileNo() {
		return imageFileNo;
	}
	public void setImageFileNo(int imageFileNo) {
		this.imageFileNo = imageFileNo;
	}
	public String getImageFileName() {
		return imageFileName;
	}
	public void setImageFileName(String imageFileName) {
		try {	
			if(imageFileName != null && imageFileName.length() != 0)
			{
				this.imageFileName = URLEncoder.encode(imageFileName, "utf-8");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public Date getImgDate() {
		return imgDate;
	}
	public void setImgDate(Date imgDate) {
		this.imgDate = imgDate;
	}
	public int getArticleNo() {
		return articleNo;
	}
	public void setArticleNo(int articleNo) {
		this.articleNo = articleNo;
	}
	
}
