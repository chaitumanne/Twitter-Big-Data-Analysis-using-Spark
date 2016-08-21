import javax.servlet.annotation.WebServlet
import javax.servlet.http.HttpServlet
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@WebServlet(Array("/"))
class Server extends HttpServlet {
  override def doGet(req: HttpServletRequest, res: HttpServletResponse) {
    res.sendRedirect("demo.html")
  }
}
