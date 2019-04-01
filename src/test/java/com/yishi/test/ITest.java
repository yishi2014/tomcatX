//package com.yishi.test;
//
//
//import java.io.UnsupportedEncodingException;
//import java.net.URLEncoder;
//import java.util.Arrays;
//
//public interface ITest {
//    public static void main(String[] args) throws UnsupportedEncodingException {
//
//        String sql="with pa as \n" +
//                " (select ?  setid,\n" +
//                "         ?  year,\n" +
//                "         ?  enguid,\n" +
//                "         ?  appguid\n" +
//                "    from dual),\n" +
//                "re0 as\n" +
//                " (select r.programguid,\n" +
//                "         r.bal00 bal,\n" +
//                "         r.id,\n" +
//                "         r.sectionincode,\n" +
//                "         r.functionincode,\n" +
//                "         r.sectionguid,\n" +
//                "         r.functionguid\n" +
//                "    from remain r, pa\n" +
//                "   where r.setid = pa.setid\n" +
//                "     and r.year = pa.year\n" +
//                "     and r.enterpriseguid = pa.enguid\n" +
//                "  ),\n" +
//                "acc as\n" +
//                " (select guid\n" +
//                "    from jczl.accountsection a, pa\n" +
//                "   where a.setid = pa.setid\n" +
//                "     and a.year = pa.year\n" +
//                "     and a.isenterprise = 1\n" +
//                "     and a.isfunction = 0\n" +
//                "     and a.isprogram = 0\n" +
//                "     and a.enabled = 1),\n" +
//                "acctree as\n" +
//                " (select distinct guid,\n" +
//                "                  in_code,\n" +
//                "                  isbn_code,\n" +
//                "                  isbn_code || name name,\n" +
//                "                  parent_guid,\n" +
//                "                  isleaf,\n" +
//                "                  debcre\n" +
//                "    from jczl.accountsection\n" +
//                "   start with guid in (select guid from acc)\n" +
//                "  connect by prior parent_guid = guid),\n" +
//                "rawdata as\n" +
//                " (select acctree.*, re0.*\n" +
//                "    from pa, acctree\n" +
//                "    left join re0\n" +
//                "      on acctree.guid = re0.sectionguid\n" +
//                "  \n" +
//                "  )\n" +
//                "select name,\n" +
//                "       nvl(bal00, 0) bal00,\n" +
//                "       decode(debcre, 'D', '借', 'C', '贷') debcre,\n" +
//                "       guid,\n" +
//                "       nvl(id, -1) id,\n" +
//                "       in_code,\n" +
//                "       level,\n" +
//                "       isleaf,\n" +
//                "       parent_guid\n" +
//                "  from (select rd.*,\n" +
//                "               (select sum(bal)\n" +
//                "                  from rawdata\n" +
//                "                 start with guid = rd.guid\n" +
//                "                connect by prior guid = parent_guid) bal00\n" +
//                "          from rawdata rd)\n" +
//                " start with parent_guid = 0\n" +
//                "connect by prior guid = parent_guid\n" +
//                " order siblings by isbn_code\n";
//
//        StringBuffer stringBuffer=new StringBuffer("insert into remain\n" +
//                "  (setid,\n" +
//                "   year,\n" +
//                "   id,\n" +
//                "   sectionguid,\n" +
//                "   functionguid,\n" +
//                "   programguid,\n" +
//                "   bal00,\n" +
//                "   isplan,\n" +
//                "   sectionincode,\n" +
//                "   functionincode,\n" +
//                "   programincode,\n" +
//                "   enterincode,\n" +
//                "   enterpriseguid) \n" +
//                "  with pa as\n" +
//                " (select ?  year,\n" +
//                "         ?     setid,\n" +
//                "         ?     appguid,\n" +
//                "         ?     startM,\n" +
//                "         ?     startd,\n" +
//                "         ?    endD,\n" +
//                "         ?    endM\n" +
//                "    from dual)," +
//                "sel_en as (select guid enguid from jczl.enterprise where guid in ("+enters+")),\n" +
//                "     sel_ac as(\n" +
//                "  select isbn_code\n" +
//                "    from jczl.accountsection\n" +
//                "   where isbn_code in ("+accountSections+")\n" +
//                " ),\n" +
//                "le0 as\n" +
//                " (select l.setid,\n" +
//                "         l.year,\n" +
//                "         l.month,\n" +
//                "         l.voucherno,\n" +
//                "         l.day,\n" +
//                "         l.sectionguid,\n" +
//                "         l.enterguid,\n" +
//                "         l.functionguid,\n" +
//                "         l.programguid,\n" +
//                "         l.debmoney,\n" +
//                "         l.cremoney\n" +
//                "    from ledger l, pa " +(((getBooksettype(setid)==1)||(getBooksettype(setid)==4))?"":", sel_en")+
//                "   where l.setid = pa.setid\n" +
//                "     and l.year = pa.year " +(((getBooksettype(setid)==1)||(getBooksettype(setid)==4))?"":"and l.enterguid = sel_en.enguid")+
//                ") ， le as\n" +
//                " (select *\n" +
//                "    from le0, pa\n" +
//                "   where (month + 100) || (day + 100) <= (pa.endm + 100) || (pa.endd + 100))\n" +
//                "\n" +
//                ",\n" +
//                "\n" +
//                "le1 as\n" +
//                " (select sectionguid,\n" +
//                "         functionguid,\n" +
//                "         programguid,\n" +
//                "         enterguid,\n" +
//                "         sum(debmoney) deb,\n" +
//                "         sum(cremoney) cre\n" +
//                "    from le\n" +
//                "   group by sectionguid, functionguid, programguid, enterguid),\n" +
//                "\n" +
//                "ac as\n" +
//                " (select a.guid,\n" +
//                "         a.name,\n" +
//                "         a.in_code,\n" +
//                "         a.code,\n" +
//                "         decode(a.debcre, 'D', 1, 'C', -1) debcre,\n" +
//                "         a.year,\n" +
//                "         a.setid,\n" +
//                "         a.enabled,\n" +
//                "         a.parent_guid,\n" +
//                "         a.isbn_code\n" +
//                "    from jczl.accountsection a, pa\n" +
//                "   where a.setid = pa.setid\n" +
//                "     and a.year = pa.year\n" +
//                "     and substr(a.isbn_code,1, instr(a.isbn_code, '.', 1, 1)) in (select isbn_code from sel_ac)\n" +
//                "  ),\n" +
//                "re0 as\n" +
//                " (select t.sectionguid,\n" +
//                "         t.bal00,\n" +
//                "         t.functionguid,\n" +
//                "         t.programguid,\n" +
//                "         t.enterpriseguid\n" +
//                "    from remain t, pa" +(((getBooksettype(setid)==1)||(getBooksettype(setid)==4))?"":", sel_en")+
//                "   where t.setid = pa.setid\n" +
//                "     and t.year = pa.year " +(((getBooksettype(setid)==1)||(getBooksettype(setid)==4))?"":"and t.enterpriseguid = sel_en.enguid")+
//                " )\n" +
//                "\n" +
//                "，\n" +
//                "\n" +
//                "re1 as\n" +
//                " (select sectionguid,\n" +
//                "         functionguid,\n" +
//                "         programguid,\n" +
//                "         enterpriseguid,\n" +
//                "         sum(bal00) bal00\n" +
//                "    from re0\n" +
//                "   group by sectionguid, functionguid, programguid, enterpriseguid)\n" +
//                "\n" +
//                ",\n" +
//                "re_un_le as\n" +
//                " (select re1.sectionguid rsectionguid,\n" +
//                "         re1.functionguid rfunctionguid,\n" +
//                "         re1.programguid rprogramguid,\n" +
//                "         re1.enterpriseguid renterpriseguid,\n" +
//                "         le1.sectionguid lsectionguid,\n" +
//                "         le1.functionguid lfunctionguid,\n" +
//                "         le1.programguid lprogramguid,\n" +
//                "         le1.enterguid lenterguid,\n" +
//                "         re1.bal00 as bal00,\n" +
//                "         le1.deb as fdeb,\n" +
//                "         le1.cre as fcre,\n" +
//                "         nvl(re1.sectionguid, le1.sectionguid) sectionguid\n" +
//                "    from re1\n" +
//                "    full join le1\n" +
//                "      on le1.sectionguid = re1.sectionguid\n" +
//                "     and ((le1.functionguid = re1.functionguid and\n" +
//                "         le1.programguid = re1.programguid) or\n" +
//                "         (le1.functionguid is null and re1.functionguid is null and\n" +
//                "         le1.programguid is null and re1.programguid is null) or\n" +
//                "         (le1.functionguid = re1.functionguid and le1.programguid is null and\n" +
//                "         re1.programguid is null) or\n" +
//                "         (le1.functionguid is null and re1.functionguid is null and\n" +
//                "         le1.programguid = re1.programguid))\n" +
//                "  )" +
//                "\n" +
//                ",\n" +
//                "\n" +
//                "resu as\n" +
//                " (select ac.guid,\n" +
//                "         nvl(rfunctionguid, lfunctionguid) functionguid,\n" +
//                "         nvl(rprogramguid, lprogramguid) programguid,\n" +
//                "         nvl(renterpriseguid, lenterguid) enterguid,\n" +
//                "         nvl(re_un_le.bal00, 0) +\n" +
//                "         (nvl(re_un_le.fdeb, 0) - nvl(re_un_le.fcre, 0)) * ac.debcre as ftotal\n" +
//                "    from ac\n" +
//                "    left join re_un_le\n" +
//                "      on ac.guid = re_un_le.sectionguid\n" +
//                "   where nvl(re_un_le.bal00, 0) +\n" +
//                "         (nvl(re_un_le.fdeb, 0) - nvl(re_un_le.fcre, 0)) * ac.debcre <> 0)\n" +
//                "\n" +
//                ",\n" +
//                "accmap as\n" +
//                " (select rt.*\n" +
//                "    from jczl.relation_tab rt, pa\n" +
//                "   where rt.year = pa.year + 1\n" +
//                "     and rt.tablename = 'accountsection'),\n" +
//                "\n" +
//                "funmap as\n" +
//                " (select rt.*\n" +
//                "    from jczl.relation_tab rt, pa\n" +
//                "   where rt.year = pa.year + 1\n" +
//                "     and rt.tablename = 'functionsection'),\n" +
//                "\n" +
//                "promap as\n" +
//                " (select rt.*\n" +
//                "    from jczl.relation_tab rt, pa\n" +
//                "   where rt.year = pa.year + 1\n" +
//                "     and rt.tablename = 'program'),\n" +
//                "enmap as\n" +
//                " (select distinct rt1.originalguid,\n" +
//                "                  e1.guid,\n" +
//                "                  e1.in_code,\n" +
//                "                  e1.name,\n" +
//                "                  rt1.newguid\n" +
//                "    from jczl.enterprise e1, jczl.relation_tab rt1, pa, sel_en\n" +
//                "   where rt1.originalguid = sel_en.enguid\n" +
//                "     and rt1.year = pa.year + 1\n" +
//                "     and rt1.tablename = 'enterprise'\n" +
//                "     and e1.guid = rt1.newguid),\n" +
//                "\n" +
//                "predata1 as\n" +
//                "  (select resu.ftotal,\n" +
//                "          enmap.newguid neguid,\n" +
//                "          accmap.newguid nsguid,\n" +
//                "          funmap.newguid nfguid,\n" +
//                "          promap.newguid npguid\n" +
//                "     from resu\n" +
//                "     left join accmap\n" +
//                "       on resu.guid = accmap.originalguid\n" +
//                "     left join funmap\n" +
//                "       on resu.functionguid = funmap.originalguid\n" +
//                "     left join promap\n" +
//                "       on resu.programguid = promap.originalguid\n" +
//                "     left join enmap \n" +
//                "       on resu.enterguid=enmap.originalguid\n" +
//                "       )\n" +
//                "\n" +
//                ", predata2 as\n" +
//                "  (select sum(ftotal) ftotal, nsguid, nfguid, npguid,neguid\n" +
//                "     from predata1\n" +
//                "    group by nsguid, nfguid, npguid,neguid)\n" +
//                "  select pa.setid,\n" +
//                "         pa.year + 1,\n" +
//                "         (select max(id)\n" +
//                "            from remain\n" +
//                "           where year = pa.year + 1\n" +
//                "             and setid = pa.setid) + rownum id,\n" +
//                "         predata2.nsguid,\n" +
//                "         predata2.nfguid,\n" +
//                "         predata2.npguid,\n" +
//                "         predata2.ftotal,\n" +
//                "         '0',\n" +
//                "         nac.in_code,\n" +
//                "         nf.in_code,\n" +
//                "         np.in_code,\n" +
//                "         enmap.in_code,\n" +
//                "         enmap.guid\n" +
//                "    from pa, predata2\n" +
//                "    left join jczl.accountsection nac\n" +
//                "      on predata2.nsguid = nac.guid\n" +
//                "    left join jczl.functionsection nf\n" +
//                "      on predata2.nfguid = nf.guid\n" +
//                "    left join jczl.program np\n" +
//                "      on predata2.npguid = np.guid\n" +
//                "     left join enmap \n" +
//                "      on predata2.neguid=enmap.guid");
//
//        StringBuffer stringBuffer=new StringBuffer(
//                "with pa as\n" +
//                        " (select ?  year,\n" +
//                        "         ?     setid,\n" +
//                        "         ?     appguid,\n" +
//                        "         ?     startM,\n" +
//                        "         ?     startd,\n" +
//                        "         ?    endD,\n" +
//                        //    "          enguid,\n" +
//                        "         ?    endM\n" +
//                        "    from dual)," +
//                        "sel_en as\n" +
//                        " (select guid enguid from jczl.enterprise where guid in (" + enters + "))," +
//                        "sel_ac as\n" +
//                        " (select isbn_code\n" +
//                        "    from jczl.accountsection\n" +
//                        "   where isbn_code in ("+accountSections+")),\n" +
//                        "le0 as\n" +
//                        " (select l.setid,\n" +
//                        "         l.year,\n" +
//                        "         l.month,\n" +
//                        "         l.voucherno,\n" +
//                        "         l.day,\n" +
//                        "         l.sectionguid,\n" +
//                        "         l.enterguid,\n" +
//                        "         l.functionguid,\n" +
//                        "         l.programguid,\n" +
//                        "         l.debmoney,\n" +
//                        "         l.cremoney\n" +
//                        "    from ledger l, pa " +(((getBooksettype(setid)==1)||(getBooksettype(setid)==4))?" ":", sel_en")+
//                        "   where l.setid = pa.setid\n" +
//                        "     and l.year = pa.year " +(((getBooksettype(setid)==1)||(getBooksettype(setid)==4))?" ":" and l.enterguid = sel_en.enguid")+
//                        ") ， le as\n" +
//                        " (select *\n" +
//                        "    from le0, pa\n" +
//                        "   where (month + 100) || (day + 100) <= (pa.endm + 100) || (pa.endd + 100))\n" +
//                        "\n" +
//                        ",\n" +
//                        "\n" +
//                        "le1 as\n" +
//                        " (select sectionguid,\n" +
//                        "         functionguid,\n" +
//                        "         programguid,\n" +
//                        "         enterguid,\n" +
//                        "         sum(debmoney) deb,\n" +
//                        "         sum(cremoney) cre\n" +
//                        "    from le\n" +
//                        "   group by sectionguid, functionguid, programguid, enterguid),\n" +
//                        "\n" +
//                        "ac as\n" +
//                        " (select a.guid,\n" +
//                        "         a.name,\n" +
//                        "         a.in_code,\n" +
//                        "         a.code,\n" +
//                        "         decode(a.debcre, 'D', 1, 'C', -1) debcre,\n" +
//                        "         a.year,\n" +
//                        "         a.setid,\n" +
//                        "         a.enabled,\n" +
//                        "         a.parent_guid,\n" +
//                        "         a.isbn_code\n" +
//                        "    from jczl.accountsection a, pa\n" +
//                        "   where a.setid = pa.setid\n" +
//                        "     and a.year = pa.year\n" +
//                        "     and substr(a.isbn_code, 1, instr(a.isbn_code, '.', 1, 1)) in\n" +
//                        "         (select isbn_code from sel_ac)\n" +
//                        "  \n" +
//                        "  ),\n" +
//                        "re0 as\n" +
//                        " (select t.sectionguid,\n" +
//                        "         t.bal00,\n" +
//                        "         t.functionguid,\n" +
//                        "         t.programguid,\n" +
//                        "         t.enterpriseguid\n" +
//                        "    from remain t, pa " +(((getBooksettype(setid)==1)||(getBooksettype(setid)==4))?" ":", sel_en")+
//                        "   where t.setid = pa.setid\n" +
//                        "     and t.year = pa.year " +(((getBooksettype(setid)==1)||(getBooksettype(setid)==4))?" ":" and t.enterpriseguid = sel_en.enguid")+
//                        ")\n" +
//                        "\n" +
//                        "，\n" +
//                        "\n" +
//                        "re1 as\n" +
//                        " (select sectionguid,\n" +
//                        "         functionguid,\n" +
//                        "         programguid,\n" +
//                        "         enterpriseguid,\n" +
//                        "         sum(bal00) bal00\n" +
//                        "    from re0\n" +
//                        "   group by sectionguid, functionguid, programguid, enterpriseguid)\n" +
//                        "\n" +
//                        ",\n" +
//                        "re_un_le as\n" +
//                        " (select re1.sectionguid rsectionguid,\n" +
//                        "         re1.functionguid rfunctionguid,\n" +
//                        "         re1.programguid rprogramguid,\n" +
//                        "         re1.enterpriseguid renterpriseguid,\n" +
//                        "         le1.sectionguid lsectionguid,\n" +
//                        "         le1.functionguid lfunctionguid,\n" +
//                        "         le1.programguid lprogramguid,\n" +
//                        "         le1.enterguid lenterguid,\n" +
//                        "         re1.bal00 as bal00,\n" +
//                        "         le1.deb as fdeb,\n" +
//                        "         le1.cre as fcre,\n" +
//                        "         nvl(re1.sectionguid, le1.sectionguid) sectionguid\n" +
//                        "    from re1\n" +
//                        "    left join le1\n" +
//                        "      on le1.sectionguid = re1.sectionguid\n" +
//                        "     and ((le1.functionguid = re1.functionguid and\n" +
//                        "         le1.programguid = re1.programguid) or\n" +
//                        "         (le1.functionguid is null and re1.functionguid is null and\n" +
//                        "         le1.programguid is null and re1.programguid is null) or\n" +
//                        "         (le1.functionguid = re1.functionguid and le1.programguid is null and\n" +
//                        "         re1.programguid is null) or\n" +
//                        "         (le1.functionguid is null and re1.functionguid is null and\n" +
//                        "         le1.programguid = re1.programguid))\n" +
//                        "  union\n" +
//                        "  select re1.sectionguid rsectionguid,\n" +
//                        "         re1.functionguid rfunctionguid,\n" +
//                        "         re1.programguid rprogramguid,\n" +
//                        "         re1.enterpriseguid renterpriseguid,\n" +
//                        "         le1.sectionguid lsectionguid,\n" +
//                        "         le1.functionguid lfunctionguid,\n" +
//                        "         le1.programguid lprogramguid,\n" +
//                        "         le1.enterguid lenterguid,\n" +
//                        "         re1.bal00 as bal00,\n" +
//                        "         le1.deb as fdeb,\n" +
//                        "         le1.cre as fcre,\n" +
//                        "         nvl(re1.sectionguid, le1.sectionguid) sectionguid\n" +
//                        "    from re1\n" +
//                        "   right join le1\n" +
//                        "      on le1.sectionguid = re1.sectionguid\n" +
//                        "     and ((le1.functionguid = re1.functionguid and\n" +
//                        "         le1.programguid = re1.programguid) or\n" +
//                        "         (le1.functionguid is null and re1.functionguid is null and\n" +
//                        "         le1.programguid is null and re1.programguid is null) or\n" +
//                        "         (le1.functionguid = re1.functionguid and le1.programguid is null and\n" +
//                        "         re1.programguid is null) or\n" +
//                        "         (le1.functionguid is null and re1.functionguid is null and\n" +
//                        "         le1.programguid = re1.programguid)))\n" +
//                        "\n" +
//                        ",\n" +
//                        "\n" +
//                        "resu as\n" +
//                        " (select ac.guid,\n" +
//                        "         nvl(rfunctionguid, lfunctionguid) functionguid,\n" +
//                        "         nvl(rprogramguid, lprogramguid) programguid,\n" +
//                        "         nvl(renterpriseguid, lenterguid) enterguid,\n" +
//                        "         nvl(re_un_le.bal00, 0) +\n" +
//                        "         (nvl(re_un_le.fdeb, 0) - nvl(re_un_le.fcre, 0)) * ac.debcre as ftotal\n" +
//                        "    from ac\n" +
//                        "    left join re_un_le\n" +
//                        "      on ac.guid = re_un_le.sectionguid\n" +
//                        "   where nvl(re_un_le.bal00, 0) +\n" +
//                        "         (nvl(re_un_le.fdeb, 0) - nvl(re_un_le.fcre, 0)) * ac.debcre <> 0)\n" +
//                        "\n" +
//                        ",\n" +
//                        "accmap as\n" +
//                        " (select rt.*\n" +
//                        "    from jczl.relation_tab rt, pa\n" +
//                        "   where rt.year = pa.year + 1\n" +
//                        "     and rt.tablename = 'accountsection'),\n" +
//                        "\n" +
//                        "funmap as\n" +
//                        " (select rt.*\n" +
//                        "    from jczl.relation_tab rt, pa\n" +
//                        "   where rt.year = pa.year + 1\n" +
//                        "     and rt.tablename = 'functionsection'),\n" +
//                        "\n" +
//                        "promap as\n" +
//                        " (select rt.*\n" +
//                        "    from jczl.relation_tab rt, pa\n" +
//                        "   where rt.year = pa.year + 1\n" +
//                        "     and rt.tablename = 'program'),\n" +
//                        "enmap as\n" +
//                        " (select distinct rt1.originalguid,\n" +
//                        "                  e1.guid,\n" +
//                        "                  e1.in_code,\n" +
//                        "                  e1.name,\n" +
//                        "                  rt1.newguid\n" +
//                        "    from jczl.relation_tab rt1\n" +
//                        "    left join jczl.enterprise e1\n" +
//                        "      on e1.guid = rt1.newguid, pa " +(((getBooksettype(setid)==1)||(getBooksettype(setid)==4))?" ":", sel_en")+
//                        "   where rt1.year = pa.year + 1\n" +
//                        "     and rt1.tablename = 'enterprise' " + (((getBooksettype(setid)==1)||(getBooksettype(setid)==4))?" ":" and rt1.originalguid = sel_en.enguid")+
//                        "),\n" +
//                        "mapcheck as\n" +
//                        " (select distinct decode(am1.guid,\n" +
//                        "                         null,\n" +
//                        "                         '【错误】"+year+"年会计科目表不存在该科目',\n" +
//                        "                         '') || decode(accmap.originalguid,\n" +
//                        "                                       null,\n" +
//                        "                                       '【错误】对应表无"+newyear+"年对应会计科目',\n" +
//                        "                                       '') ||\n" +
//                        "                  decode(am2.guid, null, '【错误】"+year+"年会计科目不存在', '') ||\n" +
//                        "                  decode(am1.isleaf, 0, '【错误】会计科目非底级', '') msg,\n" +
//                        "                  '【会计科目】' || nvl(am1.code, '') || nvl(am1.name, resu.guid) name\n" +
//                        "  \n" +
//                        "    from resu\n" +
//                        "    left join jczl.accountsection am1\n" +
//                        "      on resu.guid = am1.guid\n" +
//                        "    left join accmap\n" +
//                        "      on resu.guid = accmap.originalguid\n" +
//                        "    left join jczl.accountsection am2\n" +
//                        "      on accmap.newguid = am2.guid\n" +
//                        "  \n" +
//                        "  union\n" +
//                        "  \n" +
//                        "  select distinct decode(resu.functionguid,\n" +
//                        "                         null,\n" +
//                        "                         '',\n" +
//                        "                         decode(fm1.guid,\n" +
//                        "                                null,\n" +
//                        "                                '【错误】"+year+"年功能科目表不存在该科目',\n" +
//                        "                                '') || decode(funmap.originalguid,\n" +
//                        "                                              null,\n" +
//                        "                                              '【错误】对应表无"+newyear+"年对应功能科目',\n" +
//                        "                                              '') ||\n" +
//                        "                         decode(fm2.guid,\n" +
//                        "                                null,\n" +
//                        "                                '【错误】"+newyear+"年功能科目不存在',\n" +
//                        "                                '') ||\n" +
//                        "                         decode(fm1.isleaf, 0, '【错误】功能科目非底级', '')) msg,\n" +
//                        "                  '【功能科目】' || nvl(fm1.code, '') ||\n" +
//                        "                  nvl(fm1.name, resu.functionguid) name\n" +
//                        "  \n" +
//                        "    from resu\n" +
//                        "    left join jczl.functionsection fm1\n" +
//                        "      on resu.functionguid = fm1.guid\n" +
//                        "    left join funmap\n" +
//                        "      on resu.functionguid = funmap.originalguid\n" +
//                        "    left join jczl.functionsection fm2\n" +
//                        "      on funmap.newguid = fm2.guid\n" +
//                        "  \n" +
//                        "  union select distinct decode(resu.programguid, " +
//                        "                         null,\n" +
//                        "                         '',\n" +
//                        "                         decode(pm1.guid,\n" +
//                        "                                null,\n" +
//                        "                                '【错误】"+year+"年项目表不存在该项目',\n" +
//                        "                                '') || decode(promap.originalguid,\n" +
//                        "                                              null,\n" +
//                        "                                              '【错误】对应表无"+newyear+"年对应项目',\n" +
//                        "                                              '') ||\n" +
//                        "                         decode(pm2.guid, null, '【错误】"+newyear+"年项目不存在', '') ||\n" +
//                        "                         decode(pm1.isleaf, 0, '【错误】项目非底级', '')) msg,\n" +
//                        "                  '【项目】' || nvl(pm1.code, '') ||\n" +
//                        "                  nvl(pm1.name, resu.programguid) name\n" +
//                        "  \n" +
//                        "    from resu\n" +
//                        "    left join jczl.program pm1\n" +
//                        "      on resu.programguid = pm1.guid\n" +
//                        "    left join promap\n" +
//                        "      on resu.programguid = promap.originalguid\n" +
//                        "    left join jczl.program pm2\n" +
//                        "      on promap.newguid = pm2.guid"
//                        +
//                        "  \n" +
//                        "  union\n" +
//                        "  \n" +
//                        "  select distinct decode(resu.enterguid,\n" +
//                        "                         null,\n" +
//                        "                         '',\n" +
//                        "                         decode(em1.guid,\n" +
//                        "                                null,\n" +
//                        "                                '【错误】"+year+"年单位表不存在该单位',\n" +
//                        "                                '') || decode(enmap.originalguid,\n" +
//                        "                                              null,\n" +
//                        "                                              '【错误】对应表无"+newyear+"年对应单位',\n" +
//                        "                                              '') ||\n" +
//                        "                         decode(em2.guid, null, '【错误】"+newyear+"年单位不存在', '') ||\n" +
//                        "                         decode(em1.isleaf, 0, '【错误】单位非底级', '')) msg,\n" +
//                        "                  '【单位】' || nvl(em1.code, '') ||\n" +
//                        "                  nvl(em1.name, resu.enterguid) name\n" +
//                        "  \n" +
//                        "    from resu\n" +
//                        "    left join jczl.enterprise em1\n" +
//                        "      on resu.enterguid = em1.guid\n" +
//                        "    left join enmap\n" +
//                        "      on resu.enterguid = enmap.originalguid\n" +
//                        "    left join jczl.enterprise em2\n" +
//                        "      on enmap.newguid = em2.guid\n" +
//                        "  \n" +
//                        "  union\n" +
//                        "  select '【错误】"+year+"年单个会计科目对应"+newyear+"年多个会计科目' msg,\n" +
//                        "         '【会计科目】' || code || name\n" +
//                        "    from jczl.accountsection\n" +
//                        "   where guid in (select originalguid\n" +
//                        "                    from accmap\n" +
//                        "                   group by originalguid\n" +
//                        "                  having count(newguid) > 1)\n" +
//                        "  union\n" +
//                        "  \n" +
//                        "  select '【错误】"+year+"年单个功能科目对应"+newyear+"年多个功能科目' msg,\n" +
//                        "         '【功能科目】' || code || name\n" +
//                        "    from jczl.functionsection\n" +
//                        "   where guid in (select originalguid\n" +
//                        "                    from funmap\n" +
//                        "                   group by originalguid\n" +
//                        "                  having count(newguid) > 1)\n" +
//                        "  \n" +
//                        "  union\n" +
//                        "  select '【错误】"+year+"年单个会计科目对应"+newyear+"年多个项目' msg,\n" +
//                        "         '【项目】' || code || name\n" +
//                        "    from jczl.program\n" +
//                        "   where guid in (select originalguid\n" +
//                        "                    from promap\n" +
//                        "                   group by originalguid\n" +
//                        "                  having count(newguid) > 1)\n" +
//                        "  union\n" +
//                        "  \n" +
//                        "  select '【错误】"+year+"年单个单位对应"+newyear+"年多个单位' msg, '【单位】' || code || name\n" +
//                        "    from jczl.enterprise\n" +
//                        "   where guid in (select originalguid\n" +
//                        "                    from enmap\n" +
//                        "                   group by originalguid\n" +
//                        "                  having count(newguid) > 1)\n" +
//                        "  \n" +
//                        "  )\n" +
//                        "\n" +
//                        "select * from mapcheck where length (msg) > 0 order by name");
//        List checkList= baseDao.selectMapsBySQL(stringBuffer.toString(),Arrays.asList(params));
//        Map descmap=new HashMap();
//        descmap.put("type","check");
//        descmap.put("size",checkList==null?0:checkList.size());
//        checkList.add(descmap);
//        return checkList;
//
//
//    }
//    //1000 0000
//    //0111 1111
//    //1000 0000
//    //1110 0100
//    //1110 0011/-1
//    //0001 1100
//    //16   12
//
//}
