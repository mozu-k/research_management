package com.example.demo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

@Repository
public class LiteratureRepository {

	private JdbcTemplate jdbcTemplate;

	@Autowired
	public LiteratureRepository(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	//全選択
	public List<Literature> getAll(){
		String sql = "select*from literature;";
		//行をLiterature型の新しいインスタンスに変換する
		RowMapper<Literature> rowMapper = new BeanPropertyRowMapper<>(Literature.class);
		List<Literature> list = jdbcTemplate.query(sql, rowMapper);
		return list;
	}

	//挿入
	public void insert(Literature litera) {
		//パラメーター値を取得する
		SqlParameterSource param = new BeanPropertySqlParameterSource(litera);
		//insert文を生成
		SimpleJdbcInsert insert = new SimpleJdbcInsert(this.jdbcTemplate).withTableName("literature").usingGeneratedKeyColumns("id");
		insert.execute(param);
	}

	//削除
	public void delete(int id) {
		String sql = String.format("delete from literature where id = %d;",id);
		this.jdbcTemplate.execute(sql);
	}

	//更新
	public void edit(Literature litera) {
		String sql = String.format("update literature set title='%s', author='%s', publisher='%s', release_date='%s', ISBN='%s' where id = %d;",
				litera.getTitle(),litera.getAuthor(),litera.getPublisher(),litera.getRelease_date(),litera.getIsbn(),litera.getId());
		this.jdbcTemplate.execute(sql);
	}
}
