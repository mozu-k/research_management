package com.example.demo;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

@Repository
public class ThesisRepository {
	private JdbcTemplate jdbcTemplate;

	@Autowired
	public ThesisRepository(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	public List<Thesis> getAll(){
		String sql = "select*from thesis;";
		//行をLiterature型の新しいインスタンスに変換する
		RowMapper<Thesis> rowMapper = new BeanPropertyRowMapper<>(Thesis.class);
		List<Thesis> list = jdbcTemplate.query(sql, rowMapper);
		return list;
	}

	public void insert(Thesis thesis) {
		//パラメーター値を取得する
		SqlParameterSource param = new BeanPropertySqlParameterSource(thesis);
		//insert文を生成
		SimpleJdbcInsert insert = new SimpleJdbcInsert(this.jdbcTemplate).withTableName("thesis").usingGeneratedKeyColumns("id");
		insert.execute(param);
	}

	public void delete(int id) {
		String sql = String.format("delete from thesis where id = %d;",id);
		this.jdbcTemplate.execute(sql);
	}

	public int getNextId(String tableName) {
		String sql = String.format("SHOW TABLE STATUS LIKE '%s';",tableName);
		List<Map<String,Object>> statusList = this.jdbcTemplate.queryForList(sql);
		for(Map<String,Object> str: statusList) {
			return Integer.valueOf(String.valueOf(str.get("Auto_increment")));
		}
		return 0;
	}

	public void savePdf(String path,int id) {
		String sql = String.format("update thesis set pdf_path = '%s' where id = %d;",path,id);
		this.jdbcTemplate.execute(sql);
	}

	public void edit(Thesis thesis) {
		String sql = String.format("update thesis set titleJa='%s',titleEn='%s',author1='%s',author2='%s',author3='%s',author4='%s',author5='%s',author6='%s',abstract='%s',NAID='%s',NCID='%s',ISSN='%s',NDL_ID='%s',NDL_code='%s',pdf_path='%s' where id = %d;"
				,thesis.getTitleJa(),thesis.getTitleEn(),thesis.getAuthor1(),thesis.getAuthor2(),thesis.getAuthor3(),thesis.getAuthor4(),thesis.getAuthor5(),thesis.getAuthor6(),thesis.getAbs(),thesis.getNAID(),thesis.getNCID(),thesis.getISSN(),thesis.getNDL_ID(),thesis.getNDL_code(),thesis.getPdf_path(),thesis.getId());
		this.jdbcTemplate.execute(sql);
	}
}
