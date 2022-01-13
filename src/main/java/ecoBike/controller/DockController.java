package ecoBike.controller;

import ecoBike.repository.dock.DockRepository;

import java.sql.SQLException;
import java.util.List;

public class DockController extends BaseController {
    /**
     *
     * @param dockId
     * @return
     * @throws SQLException
     */
    public List getAllBikeAvailable(int dockId) throws SQLException {
        return new DockRepository().getAllBikeAvailable(dockId);
    }
}
