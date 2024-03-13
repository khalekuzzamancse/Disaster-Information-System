package report_form.platform_contracts

import report_form.domain.ReportFormController

/**
 * * Create the necessary dependency or component so that client module does not
 * responsible for creating un-unnecessary dependency
 * * Using `expect` instead of interface to provide  the implementation as mandatory by the platform
 * 
 */

expect object ReportFormFactory{
    fun createFormController(): ReportFormController
    
}