package controllers;

import ucar.ma2.Array;
import ucar.ma2.DataType;
import ucar.nc2.NetcdfFile;
import ucar.nc2.Variable;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by yapca on 7-4-2016.
 */
public class ReadNetCDF {

    public ReadNetCDF() {
    }

    public float[][] parseFile(String file_path) throws Exception {
        // retrieve an instance of NetCDF
        NetcdfFile file = null;
        try
        {
            file = NetcdfFile.open(file_path);
        }
        catch (Exception e) {
            e.printStackTrace();
        }


        if (file == null) {
            throw new Exception("No such file exists.");
        }

        // Go through whole file to search needed data
        ArrayList<Variable> needed_variables = new ArrayList<Variable>();
        Iterator<Variable> iter_var = file.getVariables().iterator();
        for(Variable var; iter_var.hasNext();)
        {
            var = iter_var.next();
            if(var.getFullName().equals("navigation_data/longitude") ||
                    var.getFullName().equals("navigation_data/latitude") ||
                    var.getFullName().equals("geophysical_data/chlor_a"))
            {
                needed_variables.add(var);
            }
        }

        // initialize the amount the file has
        int amount_values = 0;
        int amount_columns = needed_variables.size();
        // Check if values are float datatypes and have the same array size
        // if all conditions are correct the value size 
        for(int i = 0; i < amount_columns; i++)
        {
            Array arr_needed_variable = needed_variables.get(i).read();
            amount_values = (int)arr_needed_variable.getSize();

            if (i!=0)
                if(amount_values != (int)arr_needed_variable.getSize())
                    throw new Exception("The arrays gathered are not the same size. " + amount_values + " and " + needed_variables.get(i-1).read().getSize());
            if (!arr_needed_variable.getDataType().equals(DataType.FLOAT))
                throw new Exception("Array is not a float, while float is expected.");
        }

        // make 2D array, first dimension containing the subject second dimension containing values.
        float[][] arr_lat_long_chlor = new float[amount_columns][amount_values];
        for(int i = 0; i < amount_columns; i++)
        {
            arr_lat_long_chlor[i] = (float[])needed_variables.get(i).read().getStorage();
        }

        // close file.
        file.close();
        return arr_lat_long_chlor;
    }
}