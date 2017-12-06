package br.hfs.util.xml;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;

public class DateXMLConverter implements Converter {

    public static final String FORMATO_DATA = "dd/MM/yyyy";
    
    public static final String FORMATO_DATAHORA = "dd/MM/yyyy HH:mm:ss";
    
    public static final String FORMATO_DHARQUIVO = "yyy-MM-dd_HH_mm_ss";
	
	public void marshal(Object source, HierarchicalStreamWriter writer,
            MarshallingContext context) {
        Date data = (Date) source;
        writer.setValue(String.valueOf(data.getTime()));
    }

    public Object unmarshal(HierarchicalStreamReader reader,
            UnmarshallingContext context) {
        Date data = StringToDate(reader.getValue());
        return data;
    }

    @SuppressWarnings("rawtypes")
	public boolean canConvert(Class type) {
        return type.equals(Date.class);
    }
    
    public static Date StringToDate(String sData) {
        Date data = null;
        SimpleDateFormat sdf;
        if (sData.trim().length() > 0) {
            try {
                if (sData.trim().length() == 10)
                    sdf = new SimpleDateFormat(FORMATO_DATA);
                else
                    sdf = new SimpleDateFormat(FORMATO_DATAHORA);
                    
                data = sdf.parse(sData);
            } catch (Exception ex) {
                System.err.println("Erro na rotina StringToDate: "
                        + ex.getMessage());
            }
        }
        return data;
    }
    
}
