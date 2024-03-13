package report_form.platform_contracts

import report_form.domain.ReportFormController
import report_form.ui.form.ReportFormControllerImpl

actual object ReportFormFactory{
    /**
     * now the client does need to add extra dependency (DateUtilsCustom)to get the  ReportFormController instance
     * * because of that also the now [DateUtilsCustom] can be made internal
     */
    actual fun createFormController(): ReportFormController {
        return ReportFormControllerImpl(DateUtilsCustom())
    }

}