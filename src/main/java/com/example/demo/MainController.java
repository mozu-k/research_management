package com.example.demo;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class MainController {

	@Autowired
	private LiteratureRepository literaRepository;
	@Autowired
	private ThesisRepository thesisRepository;

	//初期画面への遷移(全選択処理)
	@RequestMapping("/")
	public String index() {
        return "index";
    }

	@RequestMapping("/literature")
	public String literaIndex(Model model) {
		List<Literature> literaList = this.literaRepository.getAll();
		model.addAttribute("litera",literaList);
        return "literature/index";
	}

	@RequestMapping("/thesis")
	public String thesisIndex(Model model) {
		List<Thesis> thesisList = this.thesisRepository.getAll();
		model.addAttribute("thesis",thesisList);
        return "thesis/index";
	}

	//新規追加画面への遷移
	@GetMapping("/literature/add")
	public String literaAdd(Literature litera) {
		return "/literature/add";
	}

	@GetMapping("/thesis/add")
	public String thesisAdd(Thesis thesis) {
		return "/thesis/add";
	}

	//追加処理
	@PostMapping("/literature/add")
	@Transactional(readOnly=false)
	public String literaInsert(@ModelAttribute Literature litera,Model model){
		String pattern = "[0-9][0-9][0-9][0-9]-[0-9][0-9]-[0-9][0-9]";
		if(litera.getTitle() == "" || litera.getAuthor() == "" || (litera.getRelease_date().matches(pattern) == false && litera.getRelease_date() != "")) {
			model.addAttribute("message","書籍名と著者名は必須項目です");
			model.addAttribute("message2","日付は0000-00-00の形で入力してください");
			return "/literature/add";
		}
		this.literaRepository.insert(litera);
		return "redirect:/literature";
	}

	//追加処理
	@PostMapping("/thesis/add")
	@Transactional(readOnly=false)
	public String thesisInsert(@ModelAttribute Thesis thesis,Model model,@RequestParam("pdf_path") String path){
		if((thesis.getTitleJa() == "" && thesis.getTitleEn() == "") || thesis.getAuthor1() == "") {
			model.addAttribute("message","論文名と著者名は必須項目です");
			return "/thesis/add";
		}
		int nextId = this.thesisRepository.getNextId("thesis");
		if(path.equals("") == false) {
			String response = savePdf(path,nextId);
			if(response.equals("ファイルのパスが見つかりません")) {
				model.addAttribute("message2",response);
				return "/thesis/add";
			}else {
				thesis.setPdf_path(response);
			}
		}
		this.thesisRepository.insert(thesis);
		return "redirect:/thesis";
	}

	//PDFをディレクトリに保存
	public String savePdf(String path,int id) {
		try {
			File inFile = new File(path);
			File  outFile = new File("C:\\ProgramData\\MySQL\\MySQL Server 8.0\\Uploads\\thesis_pdf\\"+id+".pdf");
			InputStream inputStream = new FileInputStream(inFile);
			OutputStream outputStream = new FileOutputStream(outFile);
			byte[] buffer = new byte[1024];
			int len = 0;
			while((len = inputStream.read(buffer,0,buffer.length)) != -1) {
				outputStream.write(buffer,0,len);
			}
			outputStream.flush();
			inputStream.close();
			outputStream.close();
			return "thesis_pdf\\\\"+id+".pdf";
		}catch(IOException e) {
			return "ファイルのパスが見つかりません";
		}
	}

	//削除処理
	@PostMapping("/literature/delete")
	@Transactional(readOnly=false)
	public String literaDelete(@RequestParam("id") int id) {
		this.literaRepository.delete(id);
		return "redirect:/literature";
	}

	//削除処理
	@PostMapping("/thesis/delete")
	@Transactional(readOnly=false)
	public String thesisDelete(@RequestParam("id") int id) {
		this.thesisRepository.delete(id);
		return "redirect:/thesis";
	}

	//IDからデータをとってくる
	public Thesis searchThesis(int id) {
		List<Thesis> thesisList = this.thesisRepository.getAll();
		for(Thesis thesis: thesisList) {
			if(thesis.getId() == id) {
				return thesis;
			}
 		}
		return null;
	}

	//IDからデータをとってくる
	public Literature searchLitera(int id) {
		List<Literature> literaList = this.literaRepository.getAll();
		for(Literature litera: literaList) {
			if(litera.getId() == id) {
				return litera;
			}
 		}
		return null;
	}


	//詳細を表示
	@PostMapping("/thesis/detail")
	@Transactional(readOnly=false)
	public String thesisDetail(@RequestParam("id") int id,Model model) {
		model.addAttribute("thesis",searchThesis(id));
		return "/thesis/detail";
	}

	//詳細画面を表示
	@PostMapping("/literature/detail")
	@Transactional(readOnly=false)
	public String literaDetail(@RequestParam("id") int id,Model model) {
		model.addAttribute("litera",searchLitera(id));
		return "/literature/detail";
	}

	//更新画面への遷移
	@GetMapping("/literature/edit")
	public String literaEdit(@RequestParam("id") int id,Model model,Literature literature) {
		model.addAttribute("literature",searchLitera(id));
		return "/literature/edit";
	}

	//更新処理
	@PostMapping("/literature/edit")
	@Transactional(readOnly=false)
	public String literaEdit(@ModelAttribute("literature") Literature litera,Model model) {
		String pattern = "[0-9][0-9][0-9][0-9]-[0-9][0-9]-[0-9][0-9]";
		if(litera.getTitle() == "" || litera.getAuthor() == "" || (litera.getRelease_date().matches(pattern) == false && litera.getRelease_date() != "")) {
			model.addAttribute("message","書籍名と著者名は必須項目です");
			model.addAttribute("message2","日付は0000-00-00の形で入力してください");
			model.addAttribute("literature",litera);
			return "/literature/edit";
		}
		this.literaRepository.edit(litera);
		return "redirect:/literature";
	}

	//更新画面への推移
	@GetMapping("/thesis/edit")
	public String thesisEdit(@RequestParam("id") int id,Model model,Thesis thesis) {
		model.addAttribute("thesis",searchThesis(id));
		return "/thesis/edit";
	}

	//更新処理
	@PostMapping("/thesis/edit")
	@Transactional(readOnly=false)
	public String thesisEdit(@ModelAttribute("thesis") Thesis thesis,Model model,@RequestParam("pdf_path") String path) {
		if((thesis.getTitleJa() == "" && thesis.getTitleEn() == "") || thesis.getAuthor1() == "") {
			model.addAttribute("message","論文名と著者名は必須項目です");
			model.addAttribute("thesis",thesis);
			return "/thesis/edit";
		}
		if(path.equals("") == false) {
			String response = savePdf(path,thesis.getId());
			if(response.equals("ファイルのパスが見つかりません")) {
				model.addAttribute("message2",response);
				return "/thesis/edit";
			}else {
				thesis.setPdf_path(response);
			}
		}
		this.thesisRepository.edit(thesis);
		return "redirect:/thesis";
	}

	//PDFのプレビュー表示
	@GetMapping("/thesis/preview")
	public String previewPdf(@RequestParam("pdf_path") String pdf_path,@RequestParam("id") int id,HttpServletResponse response,Model model) {
		//PDFが登録されていないときの例外処理
		if(pdf_path.equals("")) {
			model.addAttribute("message","PDFが登録されていません");
			model.addAttribute("thesis",searchThesis(id));
			return "/thesis/detail";
		}

		//レスポンスがPDF形式であることを指定
		response.setContentType("application/pdf");
		try (OutputStream outputStream = response.getOutputStream(); InputStream inputStream = new FileInputStream("C:\\ProgramData\\MySQL\\MySQL Server 8.0\\Uploads\\"+pdf_path)){
			byte[] buffer = new byte[1024];
			int len = 0;
			while((len = inputStream.read(buffer,0,buffer.length)) != -1) {
				outputStream.write(buffer,0,len);
			}
			outputStream.flush();
		}catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
