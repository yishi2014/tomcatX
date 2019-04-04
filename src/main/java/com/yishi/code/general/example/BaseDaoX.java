//package com.yishi.code.general.example;
//
//import com.yishi.code.general.dto.Value_Type;
//import com.datanew.dao.impl.BaseDaoImpl;
//import org.hibernate.SQLQuery;
//import org.hibernate.type.StandardBasicTypes;
//import org.hibernate.type.Type;
//
//import java.util.List;
//
//public class BaseDaoX extends BaseDaoImpl {
//    @Override
//    public void executeBySql(String sql, List params) {
//        SQLQuery query = this.getSession().createSQLQuery(sql);
//        if (params != null) {
//            for(int i = 0; i < params.size(); ++i) {
//                Object pa=params.get(i);
//                if(pa instanceof Value_Type){
//                    query.setParameter(i,((Value_Type) pa).val,((Value_Type) pa).type);
//                }else{
//                    query.setParameter(i, pa);
//
//                }
//            }
//        }
//
//        query.executeUpdate();
//    }
//}
