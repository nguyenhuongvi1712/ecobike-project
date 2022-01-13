package ecoBike.controller;

import ecoBike.repository.dock.DockRepository;

import java.sql.SQLException;
import java.util.List;

public class HomeController extends BaseController{
    /**
     *
     * @return
     * @throws SQLException
     */
    public List getAllDock() throws SQLException {
        return new DockRepository().getAllDock();
    }

    /**
     *
     * @return
     * @throws SQLException
     */

    public List getAvailableDock() throws SQLException {
        return new DockRepository().getAllAvailableDock();
    }

}
