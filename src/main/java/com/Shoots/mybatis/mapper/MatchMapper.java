package com.Shoots.mybatis.mapper;

import com.Shoots.domain.Match;
import org.apache.ibatis.annotations.Mapper;

import java.util.HashMap;
import java.util.List;

@Mapper
public interface MatchMapper {
    public int getListCount();
    List<Match> getMatchList(HashMap<String, Integer> map);

    void insertMatch(Match match);

    Match getDetail(int matchIdx);

    int updateMatch(Match match);

    int deleteMatch(int matchIdx);
}
