package com.rimi.ctibet.portal.controller.vo;

import com.rimi.ctibet.domain.Content;
import com.rimi.ctibet.domain.IndexManager;

public class BbsIndexManagerP {
	
		private IndexManager indexManager;
		private Content post;
		public IndexManager getIndexManager() {
			return indexManager;
		}
		public void setIndexManager(IndexManager indexManager) {
			this.indexManager = indexManager;
		}
		public Content getPost() {
			return post;
		}
		public void setPost(Content post) {
			this.post = post;
		}
		
}
