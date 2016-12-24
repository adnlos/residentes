package residentes

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Transactional(readOnly = true)
class EmpresaController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond Empresa.list(params), model:[empresaCount: Empresa.count()]
    }

    def show(Empresa empresa) {
        respond empresa
    }

    def create() {
        respond new Empresa(params)
    }

    @Transactional
    def save(Empresa empresa) {
        if (empresa == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (empresa.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond empresa.errors, view:'create'
            return
        }

        empresa.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'empresa.label', default: 'Empresa'), empresa.id])
                redirect empresa
            }
            '*' { respond empresa, [status: CREATED] }
        }
    }

    def edit(Empresa empresa) {
        respond empresa
    }

    @Transactional
    def update(Empresa empresa) {
        if (empresa == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (empresa.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond empresa.errors, view:'edit'
            return
        }

        empresa.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'empresa.label', default: 'Empresa'), empresa.id])
                redirect empresa
            }
            '*'{ respond empresa, [status: OK] }
        }
    }

    @Transactional
    def delete(Empresa empresa) {

        if (empresa == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        empresa.delete flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'empresa.label', default: 'Empresa'), empresa.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'empresa.label', default: 'Empresa'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
