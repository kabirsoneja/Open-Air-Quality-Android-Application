/**
 * Author: Kabir Soneja
 * Andrew ID: ksoneja
 * Last Modified: April 05, 2020
 */

import org.bson.Document;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/*
 * This class sets the attributes for the dashboard
 */
public class Dashboard extends HttpServlet {

    DashboardQuery dashboardQuery=null;
    @Override
    public void init(){
      dashboardQuery = new DashboardQuery();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        //Setting attributes for the logs
        List<Document> list = dashboardQuery.getLogs();
        request.setAttribute("logs",list);

        //Setting all the areas for countries
        List<String> arealist = dashboardQuery.getareas();
        request.setAttribute("areas",arealist);

        //Setting all the measurements for countries
        List<String> mList = dashboardQuery.getmeasurements();
        request.setAttribute("measurements",mList);

        //Setting the total number of searches
        long count = dashboardQuery.getTotalSearches();
        request.setAttribute("numberofsearches",count);

        //Setting the average latency
        long avg = dashboardQuery.getavgLatency();
        request.setAttribute("avglatency",avg);

        //Setting the maximum latency
        long max = dashboardQuery.getMaximumLatency();
        request.setAttribute("maxlatency",max);

        //Setting the minimum latency
        long min = dashboardQuery.getMinimumLatency();
        request.setAttribute("minlatency",min);

        //Jsp page for the air quality dashboard
        String nextView = "next.jsp";

        //Request dispatcher to redirect to the dashboard
        RequestDispatcher requestDispatcher = request.getRequestDispatcher(nextView);
        requestDispatcher.forward(request,response);

    }


}
