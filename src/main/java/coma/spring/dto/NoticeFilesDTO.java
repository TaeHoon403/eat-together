package coma.spring.dto;

import org.springframework.web.multipart.MultipartFile;

public class NoticeFilesDTO {
	
		private MultipartFile[] attfiles;

		public MultipartFile[] getAttfiles() {
			return attfiles;
		}

		public void setAttfiles(MultipartFile[] attfiles) {
			this.attfiles = attfiles;
		}

		public NoticeFilesDTO(MultipartFile[] attfiles) {
			super();
			this.attfiles = attfiles;
		}

		public NoticeFilesDTO() {
			super();
			// TODO Auto-generated constructor stub
		}

	

		
		
}
