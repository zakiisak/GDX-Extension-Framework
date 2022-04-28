package game.utils;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.HashMap;

import com.badlogic.gdx.files.FileHandle;


public class Tag {
    private HashMap<String, Object> data = new HashMap<String, Object>();

    public void write(String key, float value)
    {
        data.put(key, value);
    }

    public void write(String key, int value)
    {
        data.put(key, value);
    }
    public void write(String key, double value)
    {
        data.put(key, value);
    }
    public void write(String key, long value)
    {
        data.put(key, value);
    }
    public void write(String key, short value)
    {
        data.put(key, value);
    }
    public void write(String key, byte value)
    {
        data.put(key, value);
    }
    public void write(String key, boolean value)
    {
        data.put(key, value);
    }
    public void write(String key, String value) {data.put(key, value); }

    public void write(String key, BigInteger value)
    {
        data.put(key, value.toByteArray());
    }

    public Tag() {}

    public void writeObject(String key, Object object)
    {
        data.put(key, object);
    }


    public float getFloat(String key, float defValue)
    {
        if(containsKey(key) == false)
            return defValue;
        return (float) data.get(key);
    }

    public double getDouble(String key, double defValue)
    {
        if(containsKey(key) == false)
            return defValue;
        return (double) data.get(key);
    }

    public String getString(String key, String defValue)
    {
        if(containsKey(key) == false)
            return defValue;
        return (String) data.get(key);
    }

    public int getInt(String key, int defValue)
    {
        if(containsKey(key) == false)
            return defValue;
        return (int) data.get(key);
    }

    public short getShort(String key, short defValue)
    {
        if(containsKey(key) == false)
            return defValue;
        return (short) data.get(key);
    }

    public long getLong(String key, long defValue)
    {
        if(containsKey(key) == false)
            return defValue;
        return (long) data.get(key);
    }

    public boolean getBoolean(String key, boolean defValue)
    {
        if(containsKey(key) == false)
            return defValue;
        return (boolean) data.get(key);
    }

    public BigInteger getBigInteger(String key, BigInteger defValue)
    {
        if(containsKey(key) == false)
            return defValue;
        return new BigInteger((byte[])data.get(key));
    }


    public Object getObject(String key, Object defValue)
    {
        if(containsKey(key) == false)
            return defValue;
        return data.get(key);
    }

    public void addFromFile(FileHandle file)
    {
        String[] data = file.readString().split("\n");
        for(int i = 0; i < data.length; i++)
        {
            String line = data[i];
            char first = line.charAt(0);
            Object val = null;
            final int indexOfEquals = line.indexOf("=");
            String valString = line.substring(indexOfEquals + 1);
            if(first == 'F')
            {
                val = Float.parseFloat(valString);
            }
            else if(first == 'I')
            {
                val = Integer.parseInt(valString);
            }
            else if(first == 'B')
            {
                val = Boolean.parseBoolean(valString);
            }
            else if(first == 'D')
            {
                val = Double.parseDouble(valString);
            }
            else if(first == 'H')
            {
                val = Short.parseShort(valString);
            }
            else if(first == 'S')
            {
                val = valString;
            }
            else if(first == 'L')
            {
                val = Long.parseLong(valString);
            }
            else if(first == 'G')
            {
                val = new BigInteger(valString).toByteArray();
            }
            else if(first == 'E')
            {
                val = new BigDecimal(valString);
            }

            if(val != null)
            {
                String key = line.substring(1, indexOfEquals);
                this.data.put(key, val);
            }
        }
    }

    private static final String lineSeperator = "\n";
    private static String writeTag(Tag tag, String lineSeperator) {

        String output = "";
        for(String key : tag.data.keySet()) {
            Object value = tag.data.get(key);
            String out = null;
            if (value instanceof Float) {
                out = "F" + key + "=" + value;
            } else if (value instanceof Integer) {
                out = "I" + key + "=" + value;
            } else if (value instanceof Boolean) {
                out = "B" + key + "=" + value;
            } else if (value instanceof Double) {
                out = "D" + key + "=" + value;
            } else if (value instanceof Short) {
                out = "H" + key + "=" + value;
            } else if (value instanceof String) {
                out = "S" + key + "=" + value;
            } else if (value instanceof Long) {
                out = "L" + key + "=" + value;
            }
            else if (value instanceof BigInteger) {
                out = "G" + key + "=" + value;
            } else if (value instanceof byte[]) {
                out = "G" + key + "=" + new BigInteger((byte[]) value);
            } else if (value instanceof BigDecimal) {
                out = "E" + key + "=" + value;
            }

            if (out != null) output += out + lineSeperator;
        }
        return output;
    }

    public void saveToFile(FileHandle file)
    {
        if(file.exists())
        {
            file.delete();
        }

        file.writeString(writeTag(this, lineSeperator), false);

    }

    private static Tag readFromString(String file, String seperator)
    {
        Tag tag = new Tag();
        String[] splitted = file.split(seperator);
        for(int i = 0; i < splitted.length; i++)
        {
            final String line = splitted[i].replaceAll("\\r\\n|\\r|\\n", "");
            char first = line.charAt(0);
            String key = line.substring(1, line.indexOf('='));
            if(first == 'F')
            {
                float value = Float.parseFloat(line.split("=")[1]);
                tag.write(key, value);
            }
            else if(first == 'I')
            {
                int value = Integer.parseInt(line.split("=")[1]);
                tag.write(key, value);
            }
            else if(first == 'B')
            {
                boolean value = Boolean.parseBoolean(line.split("=")[1]);
                tag.write(key, value);
            }
            else if(first == 'D')
            {
                double value = Double.parseDouble(line.split("=")[1]);
                tag.write(key, value);
            }
            else if(first == 'H')
            {
                short value = Short.parseShort(line.split("=")[1]);
                tag.write(key, value);
            }
            else if(first == 'S')
            {
                String value = line.split("=")[1];
                tag.write(key, value);
            }
            else if(first == 'L')
            {
                long value = Long.parseLong(line.split("=")[1]);
                tag.write(key, value);
            }
            else if(first == 'G')
            {
                BigInteger value = new BigInteger(line.split("=")[1]);
                tag.write(key, value);
            }
        }
        return tag;
    }

    public static Tag loadFromFile(FileHandle handle)
    {
        if(handle.exists())
        {
            String file = handle.readString();
            if(file.length() > 2)
            {
                Tag tag = readFromString(file, lineSeperator);
                return tag;
            }
            else return null;
        }
        else return null;
    }

    public void importFrom(Tag tag)
    {
        for(String key : tag.data.keySet())
        {
            this.data.put(key, tag.data.get(key));
        }
    }


    public boolean containsKey(String key)
    {
        return data.containsKey(key);
    }


}
