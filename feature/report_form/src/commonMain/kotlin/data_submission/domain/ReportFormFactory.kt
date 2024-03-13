package data_submission.domain

/**
 * * Create the necessary dependency or component so that client module does not responsible for creating un-unnecessary dependency
 * 
 */

interface ReportFormFactory{
    fun createFormController(): ReportFormController
    
}