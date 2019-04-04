package com.yishi.code.general.basedata;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * Created by Lustin on 2017/11/21.
 */
public class AddMethod {
    public static void main(String[] args) throws IOException {
//        CodeGenerator.generalCode("Result saveUpdate(BdUser user)", UserController.class);

//          CodeGenerator.generalCode("Result saveUpdate (BdDepartment department)", DeptController.class);
//
//        CodeGenerator.generalCode("Result remove(Number id)", DeptController.class);
//        CodeGenerator.generalCode("Result remove(Number id)", UserController.class);
//        CodeGenerator.generalCode("Result remove(Number id)", PostController.class);
//        CodeGenerator.generalCode("Result remove(Number id)", MenuController.class);
//        CodeGenerator.generalCode("Result remove(Number id)", RoleController.class);
//        CodeGenerator.generalCode("List tree()", PostController.class);
//        CodeGenerator.generalCode("Result remove(Number guid)", RegionController.class);
//        CodeGenerator.generalCode("List tree()", RegionController.class);
//        CodeGenerator.generalCode("List tree()", RegionController.class);
//        CodeGenerator.generalCode("List tree()", RegionController.class);
//        CodeGenerator.generalCode("List tree()", MenuController.class);
//        CodeGenerator.generalCode("List getMenuIds(Number roleId)", MenuController.class);
//        CodeGenerator.generalCode("List getRoleIds(Number postId)", PostController.class);
//        CodeGenerator.generalCode("List pageData()", PostController.class);
//        CodeGenerator.generalCode("Result roleData()", RoleController.class);
//        CodeGenerator.generalCode("Result roleData()", FileController.class);
//        CodeGenerator.generalCode("Result delete(String step,int guid)", FileController.class);
//        CodeGenerator.generalCode("void downLoad(String uri, HttpServletResponse response)", FileController.class);
//        CodeGenerator.generalCode("List<BizFile>getFilesByStep(int guid,String step)", LoanApplyController.class);
//        CodeGenerator.generalCode("Result step (int guid,String status,String remark)", LoanApplyController.class);
//        CodeGenerator.generalCode("List getStepDetail (int guid,String step)", LoanApplyController.class);
//        CodeGenerator.generalCode("List getRatesSelection (int loanTypeId,String repayMethodCode,int loanTermId)", LoanApplyController.class);
//        CodeGenerator.generalCode("List list (int loanTypeId,String repayMethodCode,int loanTermId)", LoanRateController.class);
//        CodeGenerator.generalCode("Result remove (int loanTypeId,String repayMethodCode,int loanTermId)", LoanRateController.class);
//        CodeGenerator.generalCode("Map refreshToken (String token,String refreshToken)", LogInController.class);
//        CodeGenerator.generalCode("void getKaptchaImage ( HttpServletRequest request,HttpServletResponse response)", LogInController.class);
//        CodeGenerator.generalCode("Result getKaptchaImage (String code,String key)", LogInController.class);
//        CodeGenerator.generalCode("PageData page(Page page)", LoanRateController.class);
//        CodeGenerator.generalCode("List list()", RepayMethodController.class);
//        CodeGenerator.generalCode("Result saveUpdate(BdRepaymethod bdRepaymethod)", RepayMethodController.class);
//        CodeGenerator.generalCode("Result remove (String methodCode)", RepayMethodController.class);
//        CodeGenerator.generalCode("List list ()", RegionController.class);
//        CodeGenerator.generalCode("PageData page (Page page)", RegionController.class);
//        CodeGenerator.generalCode("List list (Page page)", LoanTypeController.class);
//        CodeGenerator.generalCode("Result remove (int guid)", LoanTypeController.class);
//        CodeGenerator.generalCode("Result saveUpdata (LoanType loanType)", LoanTypeController.class);
//        CodeGenerator.generalCode("List list ()", EstimateController.class);
//        CodeGenerator.generalCode("PageData page(Page page)", EstimateController.class);
//        CodeGenerator.generalCode("Result saveUpdate(BizEstimateApply bizEstimateApply)", EstimateController.class);
//        CodeGenerator.generalCode("Result remove (int guid)", EstimateController.class);
//        CodeGenerator.generalCode("Map upload(MultipartFile files,s String token)", FileController.class);
//        CodeGenerator.generalCode("Result delete(String uuid)", FileController.class);
//        CodeGenerator.generalCode("Result remove(int uuid)", BankInfoController.class);
//        CodeGenerator.generalCode("Result saveUpdate(BankBankinfo bank)", BankInfoController.class);
//        CodeGenerator.generalCode("Result storeBase64Img(String base64,String token)", FileController.class);
//        BizAuthorizeApply bizAuthorizeApply;

//        CodeGenerator.generalCode("Map pass(BizEstimateApply bizEstimateApply)", EstimateController.class);
//        CodeGenerator.generalCode("Map reject(BizEstimateApply bizEstimateApply)", EstimateController.class);
//        CodeGenerator.generalCode("Map pass(BizAuthorizeApply bizAuthorizeApply)", AuthorizeApplyController.class);
//        CodeGenerator.generalCode("Map reject(BizAuthorizeApply bizAuthorizeApply)", AuthorizeApplyController.class);
//        BizLoanApply bizLoanApply;
//        CodeGenerator.generalCode("Map pass(BizLoanApply bizLoanApply)", LoanApplyController.class);
//        CodeGenerator.generalCode("Map reject(BizLoanApply bizLoanApply)", LoanApplyController.class);

//        CodeGenerator.generalCode("PageData estimatePage(Page page)", EstimateController.class);
//
//        CodeGenerator.generalCode("Map estimatePass(BizEstimateApply bizEstimateApply)", EstimateController.class);
//        CodeGenerator.generalCode("Map estimateReject(BizEstimateApply bizEstimateApply)", EstimateController.class);
//        CodeGenerator.generalCode("Map passCreditApproval(BizAuthorizeApply bizAuthorizeApply)", AuthorizeApplyController.class);
//        CodeGenerator.generalCode("Map rejectCreditApproval(BizAuthorizeApply bizAuthorizeApply)", AuthorizeApplyController.class);
//        CodeGenerator.generalCode("PageData pageCreditApproval(Page page)", AuthorizeApplyController.class);
//
//        CodeGenerator.generalCode("Map passCreditLine(BizAuthorizeApply bizAuthorizeApply)", AuthorizeApplyController.class);
//        CodeGenerator.generalCode("Map rejectCreditLine(BizAuthorizeApply bizAuthorizeApply)", AuthorizeApplyController.class);
//        CodeGenerator.generalCode("PageData pageCreditLine(Page page)", AuthorizeApplyController.class);
//
//        CodeGenerator.generalCode("Map passCreditMortgage(BizAuthorizeApply bizAuthorizeApply)", AuthorizeApplyController.class);
//        CodeGenerator.generalCode("Map rejectCreditMortgage(BizAuthorizeApply bizAuthorizeApply)", AuthorizeApplyController.class);
//        CodeGenerator.generalCode("PageData pageCreditMortgage(Page page)", AuthorizeApplyController.class);//
//
//        public static final String LOANAPPLY_APPLY="LOAN_APPLY";//贷款申请
//        public static final String LOANAPPLY_APPROVAL="LOAN_APPROVAL";//贷款审批
//        public static final String LOANAPPLY_CHECK="LOAN_CHECK";//贷款审核
//        public static final String LOANAPPLY_CHARGE="LOAN_CHARGE";//服务费缴纳
//        public static final String LOANAPPLY_CONTRACT="LOAN_CONTRACT";//贷款合同录入
//        public static final String LOANAPPLY_LOAN="LOAN_LOAN";//放款
//        public static final String LOANAPPLY_ENDING="LOAN_ENDING";//还款结束;
//        CodeGenerator.generalCode("Map passLoanApproval(BizLoanApply bizLoanApply)", LoanApplyController.class);
//        CodeGenerator.generalCode("Map rejectLoanApproval(BizLoanApply bizLoanApply)", LoanApplyController.class);
//        CodeGenerator.generalCode("PageData pageLoanApproval(Page page)", LoanApplyController.class);
//
//        CodeGenerator.generalCode("Map passLoanCheck(BizLoanApply bizLoanApply)", LoanApplyController.class);
//        CodeGenerator.generalCode("Map rejectLoanCheck(BizLoanApply bizLoanApply)", LoanApplyController.class);
//        CodeGenerator.generalCode("PageData pageLoanCheck(Page page)", LoanApplyController.class);
//
//        CodeGenerator.generalCode("Map passLoanContract(BizLoanApply bizLoanApply)", LoanApplyController.class);
//        CodeGenerator.generalCode("Map rejectLoanContract(BizLoanApply bizLoanApply)", LoanApplyController.class);
//        CodeGenerator.generalCode("PageData pageLoanContract(Page page)", LoanApplyController.class);

//        CodeGenerator.generalCode("Map passLoanLoan(BizLoanApply bizLoanApply)", LoanApplyController.class);
//        CodeGenerator.generalCode("Map rejectLoanLoan(BizLoanApply bizLoanApply)", LoanApplyController.class);
//        CodeGenerator.generalCode("PageData pageLoanLoan(Page page)", LoanApplyController.class);
//        CodeGenerator.generalCode("List rateAndTerm()", LoanApplyController.class);
//        CodeGenerator.generalCode("List queryRepay(int guid)", LoanApplyController.class);







    }
}
