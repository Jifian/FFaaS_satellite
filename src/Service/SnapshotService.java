package Service;

import DAO.MainDAO;
import Entities.Snapshots;
import Utils.EntityEnum;

import java.util.List;

/**
 * Created by yapca on 26-4-2016.
 */
public class SnapshotService extends MainDAO
{
    public boolean add(Snapshots snapshot)
    {
        return super.add(snapshot);
    }

    public List<Snapshots> List()
    {
        return getList(EntityEnum.Snapshots);
    }

    public void delete(int id)
    {
        super.delete(id);
    }

    public void update(Snapshots snapshot)
    {
        super.update(snapshot);
    }
}
