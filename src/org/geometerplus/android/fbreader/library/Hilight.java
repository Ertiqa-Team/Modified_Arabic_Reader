package org.geometerplus.android.fbreader.library;

import org.geometerplus.zlibrary.text.view.ZLTextPosition;

public class Hilight {
	String bookId;
	private ZLTextPosition start;
	private ZLTextPosition end;
	
	public Hilight(String bookId,ZLTextPosition start,ZLTextPosition end){
		this.bookId = bookId;
		this.start = start;
		this.end = end;
	}
	
	public String getBookId(){return this.bookId;}
	public ZLTextPosition getStart(){return this.start;}
	public ZLTextPosition getEnd(){return this.end;}
	
	public void setBookId(String bookId){this.bookId=bookId;}
	public void setStart(ZLTextPosition start){this.start = start;}
	public void setEnd(ZLTextPosition end){this.end = end;}
}
