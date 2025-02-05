package com.Shoots.service.report;

import com.Shoots.domain.Report;

import java.util.List;
import java.util.Map;

public interface ReportService {
    int insertReport(Report report);
    List<Report> selectReportedUsers(String reporter);
    public Report selectCheckReportDuplicate(String reporter,int PostIdx, int CommentIdx, String category);
    public int selectReportedCount(String reported,String category);
    public List<Map<String, Object>> getReportList(int page, int limit);
    public int getReportCount();
}
