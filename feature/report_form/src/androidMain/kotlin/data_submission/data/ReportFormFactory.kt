package data_submission.data

import data_submission.domain.ReportFormFactory
import data_submission.platform_contracts.DateUtilsCustom
import data_submission.domain.ReportFormController
import data_submission.ui.form.ReportFormControllerImpl


object ReportFormFactoryImpl : ReportFormFactory {
    /**
     * now the client does need to add extra dependency (DateUtilsCustom)to get the  ReportFormController instance
     * * because of that also the now [DateUtilsCustom] can be made internal
     */
    override fun createFormController(): ReportFormController {
        return ReportFormControllerImpl(DateUtilsCustom())
    }

}