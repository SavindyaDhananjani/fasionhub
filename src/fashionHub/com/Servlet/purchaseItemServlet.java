package fashionHub.com.Servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import fashionHub.com.DBUtil.CartDBUtil;
import fashionHub.com.DBUtil.PurchaseDBUtil;
import fashionHub.com.Object.Purchase;


@WebServlet("/purchaseItemServlet")
public class purchaseItemServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

    public purchaseItemServlet() {
        super();
    }

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//Get session data
		HttpSession session = request.getSession(false);
		String username = (String)session.getAttribute("user");
		System.out.println(username);
								
		//Create a new session to pass the username
		HttpSession sessionpass = request.getSession();
		sessionpass.setAttribute("user", username);
		
		//Get parameter Values
		String firstname = request.getParameter("firstname");
		String middlename = request.getParameter("middlename");
		String companyname = request.getParameter("companyname");
		String email = request.getParameter("email");
		String tp = request.getParameter("tp");
		String address = request.getParameter("address");
		String country = request.getParameter("country");
		String raod = request.getParameter("raod");
		String city = request.getParameter("city");
		String distric = request.getParameter("distric");
		String zip = request.getParameter("zip");
		String sfirstname = request.getParameter("sfirstname");
		String slastname = request.getParameter("slastname");
		String scompanyname = request.getParameter("scompanyname");
		String semail = request.getParameter("semail");
		String stp = request.getParameter("stp");
		String saddress = request.getParameter("saddress");
		String scountry = request.getParameter("scountry");
		String sroad = request.getParameter("sroad");
		String scity = request.getParameter("scity");
		String sdistric = request.getParameter("sdistric");
		String szip = request.getParameter("szip");
		String notes = request.getParameter("notes");
		String subtotal = request.getParameter("subtotal");
		String tax = request.getParameter("tax");
		String total = request.getParameter("total");
		String methodpay = request.getParameter("methodpay");
		
		//Create Purchase Object
		Purchase pr = new Purchase();
		
		//Craete PurchaseDBUtil Object
		PurchaseDBUtil pdb = new PurchaseDBUtil();
			
		//Add data to pr object
		pr.setFirstname(sfirstname);
		pr.setMiddlename(middlename);
		pr.setCompanyname(scompanyname);
		pr.setEmail(semail);
		pr.setTp(stp);
		pr.setAddress(saddress);
		pr.setCountry(scountry);
		pr.setRaod(raod);
		pr.setCity(scity);
		pr.setDistric(sdistric);
		pr.setZip(szip);
		pr.setSfirstname(sfirstname);
		pr.setSlastname(slastname);
		pr.setScompanyname(scompanyname);
		pr.setSemail(semail);
		pr.setSemail(semail);
		pr.setStp(stp);
		pr.setSaddress(saddress);
		pr.setScountry(scountry);
		pr.setSroad(sroad);
		pr.setScity(scity);
		pr.setSdistric(sdistric);
		pr.setSzip(szip);
		pr.setNotes(notes);
		pr.setTotal(total);
		pr.setTax(tax);
		pr.setSubtotal(subtotal);
		pr.setMethodpay(methodpay);
		pr.setUsername(username);
		
		//Call method to add purchase
		String sts = pdb.addPurchase(pr);
		
		if(sts.equals("SUCCESS")) {
			CartDBUtil crt = new CartDBUtil();
			crt.removeCartAll(username);
			ServletHandler.setSuccessMessage("Purchased Successfully!", request);
			ServletHandler.forward("checkout.jsp", request, response);
		}
		else {
			ServletHandler.setErrorMessage("Something went wrong! Please try again!", request);
			ServletHandler.forward("checkout.jsp", request, response);
		}
	}

}
