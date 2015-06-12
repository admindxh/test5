package com.rimi.ctibet.web.controller.vo;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;

public class InfoLinkManage {
	public static String path;
	public static ServletContext appliction;
	private List<InfoLink> infoLinks = new ArrayList();
	public boolean progLinks(String links, String codes, String regex) {
		String ls[] = links.split(regex);
		String cs[] = codes.split(regex);
		//System.out.println(ls.length);
		//System.out.println(cs.length);
		if (ls.length != cs.length) {
			return false;
		} else {
			for (int i = 0; i < ls.length; i++) {
				InfoLink infoLink = new InfoLink();
				infoLink.setLink(ls[i]);
				infoLink.setCode(cs[i]);
				infoLink.setId("" + i);
				infoLinks.add(infoLink);
			}
		}
		return true;
	}
	
	private void clearFromAppliction(String name){
		if(appliction!=null){
			appliction.removeAttribute(name);
		}
	}
	public void saveLinks(String name) {
		clearFromAppliction(name);
		String fileDir=this.path+"/data/";
		File dirs=new File(fileDir);
		if(!dirs.exists())
		{
			dirs.mkdirs();
		}
		File file = new File(fileDir+name + ".dat");
		//System.out.println("save:"+name+"  list size="+infoLinks.size());
		checkFile(file);
		FileOutputStream out;
		try {
			out = new FileOutputStream(file);
			ObjectOutputStream objOut = new ObjectOutputStream(out);
			objOut.writeObject(infoLinks);
			objOut.flush();
			objOut.close();
			//System.out.println("write object success!");
		} catch (IOException e) {
			//System.out.println("write object failed");
			e.printStackTrace();
		}

	}

	private void checkFile(File file) {
		if (!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	private boolean readFromAppliction(String name){
		if(appliction!=null&&appliction.getAttribute(name)==null){
			return false;
		}else if(appliction!=null&&appliction.getAttribute(name)!=null){
			infoLinks=(List<InfoLink>)appliction.getAttribute(name);
			return true;
		}else
		{
			return false;
		}
	}
	
	private void save2Appliction(String name){
		if(appliction!=null){
			appliction.removeAttribute(name);
			appliction.setAttribute(name,infoLinks);
		}
	}
	public void readLinks(String name) {
		if(readFromAppliction(name)){
			return ;
		}
		String fileDir=this.path+"/data/";
		File dirs=new File(fileDir);
		if(!dirs.exists())
		{
			dirs.mkdirs();
		}
		Object temp = null;
		File file = new File(fileDir+name + ".dat");
		checkFile(file);
		FileInputStream in;
		try {
			in = new FileInputStream(file);
			ObjectInputStream objIn = new ObjectInputStream(in);
			temp = objIn.readObject();
			infoLinks = (List<InfoLink>) temp;
			objIn.close();
			//System.out.println("read object success!");
		} catch (IOException e) {
			//System.out.println("read object failed");
			//e.printStackTrace();
		} catch (ClassNotFoundException e) {
			//e.printStackTrace();
		}
		save2Appliction(name);
	}
	public List<InfoLink> getInfoLinks() {
		return infoLinks;
	}

	public void setInfoLinks(List<InfoLink> infoLinks) {
		this.infoLinks = infoLinks;
	}

	public boolean progLinks(String links, String codes) {
		return progLinks(links, codes, ",");
	}
	
	public boolean progLinks(String[] links,String[] codes){
		int i=0;
		infoLinks.clear();
		for (String string : codes) {
				InfoLink infoLink = new InfoLink();
				infoLink.setCode(string);
				infoLink.setLink(links[i]);
				infoLinks.add(infoLink);
				i++;
		}
		return true;
	}

}
