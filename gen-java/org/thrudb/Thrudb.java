/**
 * Autogenerated by Thrift
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 */
package org.thrudb;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.Set;
import java.util.HashSet;
import java.util.Collections;

import org.apache.thrift.*;
import org.apache.thrift.meta_data.*;
import org.apache.thrift.protocol.*;

public class Thrudb {

  /**
   * Base service calls all thrudb services must implement.
   * 
   * Primarily for KPI and Replication purposes.
   * 
   */
  public interface Iface {

    /**
     * Reteieves a map of data about this service.
     * 
     * There are many kinds of data:
     * 
     * Service call counts -
     * 		All keys will start with "mc_", example: "mc_get"
     * 		All values will represent number of times invoked
     * 					  
     * Service call message sizes -
     * 		All keys will start with "ms_"
     * 		All values will represent total bytes recieved
     *  
     * Service memory/cpu usage, uptime and health -
     * 			key:"heap",  value:heapsize in kb
     * 		key:"cpu",   value:0-100 representing %cpu
     * 		key:"uptime",value:seconds since start
     * 
     * 
     * Note, this data is ephemeral so if the service is restarted the previous
     * stats are lost.
     */
    public Map<String,Long> getServiceStats() throws TException;

    /**
     * Acts as a noop, for debug and monitoring purposes.
     */
    public void ping() throws TException;

    /**
     * Will return a number of binary requests from the redo logs.
     * 
     * @param lsn
     *            The log sequence number to start from (inclusive)
     * @param kbLimit
     * 		  The max response size of the messages (not strict)
     * 
     * @param lsn
     * @param kbLimit
     */
    public List<logEntry> getLogFrom(String lsn, int kbLimit) throws TException;

  }

  public static class Client implements Iface {
    public Client(TProtocol prot)
    {
      this(prot, prot);
    }

    public Client(TProtocol iprot, TProtocol oprot)
    {
      iprot_ = iprot;
      oprot_ = oprot;
    }

    protected TProtocol iprot_;
    protected TProtocol oprot_;

    protected int seqid_;

    public TProtocol getInputProtocol()
    {
      return this.iprot_;
    }

    public TProtocol getOutputProtocol()
    {
      return this.oprot_;
    }

    public Map<String,Long> getServiceStats() throws TException
    {
      send_getServiceStats();
      return recv_getServiceStats();
    }

    public void send_getServiceStats() throws TException
    {
      oprot_.writeMessageBegin(new TMessage("getServiceStats", TMessageType.CALL, seqid_));
      getServiceStats_args args = new getServiceStats_args();
      args.write(oprot_);
      oprot_.writeMessageEnd();
      oprot_.getTransport().flush();
    }

    public Map<String,Long> recv_getServiceStats() throws TException
    {
      TMessage msg = iprot_.readMessageBegin();
      if (msg.type == TMessageType.EXCEPTION) {
        TApplicationException x = TApplicationException.read(iprot_);
        iprot_.readMessageEnd();
        throw x;
      }
      getServiceStats_result result = new getServiceStats_result();
      result.read(iprot_);
      iprot_.readMessageEnd();
      if (result.isSetSuccess()) {
        return result.success;
      }
      throw new TApplicationException(TApplicationException.MISSING_RESULT, "getServiceStats failed: unknown result");
    }

    public void ping() throws TException
    {
      send_ping();
      recv_ping();
    }

    public void send_ping() throws TException
    {
      oprot_.writeMessageBegin(new TMessage("ping", TMessageType.CALL, seqid_));
      ping_args args = new ping_args();
      args.write(oprot_);
      oprot_.writeMessageEnd();
      oprot_.getTransport().flush();
    }

    public void recv_ping() throws TException
    {
      TMessage msg = iprot_.readMessageBegin();
      if (msg.type == TMessageType.EXCEPTION) {
        TApplicationException x = TApplicationException.read(iprot_);
        iprot_.readMessageEnd();
        throw x;
      }
      ping_result result = new ping_result();
      result.read(iprot_);
      iprot_.readMessageEnd();
      return;
    }

    public List<logEntry> getLogFrom(String lsn, int kbLimit) throws TException
    {
      send_getLogFrom(lsn, kbLimit);
      return recv_getLogFrom();
    }

    public void send_getLogFrom(String lsn, int kbLimit) throws TException
    {
      oprot_.writeMessageBegin(new TMessage("getLogFrom", TMessageType.CALL, seqid_));
      getLogFrom_args args = new getLogFrom_args();
      args.lsn = lsn;
      args.kbLimit = kbLimit;
      args.write(oprot_);
      oprot_.writeMessageEnd();
      oprot_.getTransport().flush();
    }

    public List<logEntry> recv_getLogFrom() throws TException
    {
      TMessage msg = iprot_.readMessageBegin();
      if (msg.type == TMessageType.EXCEPTION) {
        TApplicationException x = TApplicationException.read(iprot_);
        iprot_.readMessageEnd();
        throw x;
      }
      getLogFrom_result result = new getLogFrom_result();
      result.read(iprot_);
      iprot_.readMessageEnd();
      if (result.isSetSuccess()) {
        return result.success;
      }
      throw new TApplicationException(TApplicationException.MISSING_RESULT, "getLogFrom failed: unknown result");
    }

  }
  public static class Processor implements TProcessor {
    public Processor(Iface iface)
    {
      iface_ = iface;
      processMap_.put("getServiceStats", new getServiceStats());
      processMap_.put("ping", new ping());
      processMap_.put("getLogFrom", new getLogFrom());
    }

    protected static interface ProcessFunction {
      public void process(int seqid, TProtocol iprot, TProtocol oprot) throws TException;
    }

    private Iface iface_;
    protected final HashMap<String,ProcessFunction> processMap_ = new HashMap<String,ProcessFunction>();

    public boolean process(TProtocol iprot, TProtocol oprot) throws TException
    {
      TMessage msg = iprot.readMessageBegin();
      ProcessFunction fn = processMap_.get(msg.name);
      if (fn == null) {
        TProtocolUtil.skip(iprot, TType.STRUCT);
        iprot.readMessageEnd();
        TApplicationException x = new TApplicationException(TApplicationException.UNKNOWN_METHOD, "Invalid method name: '"+msg.name+"'");
        oprot.writeMessageBegin(new TMessage(msg.name, TMessageType.EXCEPTION, msg.seqid));
        x.write(oprot);
        oprot.writeMessageEnd();
        oprot.getTransport().flush();
        return true;
      }
      fn.process(msg.seqid, iprot, oprot);
      return true;
    }

    private class getServiceStats implements ProcessFunction {
      public void process(int seqid, TProtocol iprot, TProtocol oprot) throws TException
      {
        getServiceStats_args args = new getServiceStats_args();
        args.read(iprot);
        iprot.readMessageEnd();
        getServiceStats_result result = new getServiceStats_result();
        result.success = iface_.getServiceStats();
        oprot.writeMessageBegin(new TMessage("getServiceStats", TMessageType.REPLY, seqid));
        result.write(oprot);
        oprot.writeMessageEnd();
        oprot.getTransport().flush();
      }

    }

    private class ping implements ProcessFunction {
      public void process(int seqid, TProtocol iprot, TProtocol oprot) throws TException
      {
        ping_args args = new ping_args();
        args.read(iprot);
        iprot.readMessageEnd();
        ping_result result = new ping_result();
        iface_.ping();
        oprot.writeMessageBegin(new TMessage("ping", TMessageType.REPLY, seqid));
        result.write(oprot);
        oprot.writeMessageEnd();
        oprot.getTransport().flush();
      }

    }

    private class getLogFrom implements ProcessFunction {
      public void process(int seqid, TProtocol iprot, TProtocol oprot) throws TException
      {
        getLogFrom_args args = new getLogFrom_args();
        args.read(iprot);
        iprot.readMessageEnd();
        getLogFrom_result result = new getLogFrom_result();
        result.success = iface_.getLogFrom(args.lsn, args.kbLimit);
        oprot.writeMessageBegin(new TMessage("getLogFrom", TMessageType.REPLY, seqid));
        result.write(oprot);
        oprot.writeMessageEnd();
        oprot.getTransport().flush();
      }

    }

  }

  public static class getServiceStats_args implements TBase, java.io.Serializable, Cloneable   {
    private static final TStruct STRUCT_DESC = new TStruct("getServiceStats_args");

    public static final Map<Integer, FieldMetaData> metaDataMap = Collections.unmodifiableMap(new HashMap<Integer, FieldMetaData>() {{
    }});

    static {
      FieldMetaData.addStructMetaDataMap(getServiceStats_args.class, metaDataMap);
    }

    public getServiceStats_args() {
    }

    /**
     * Performs a deep copy on <i>other</i>.
     */
    public getServiceStats_args(getServiceStats_args other) {
    }

    @Override
    public getServiceStats_args clone() {
      return new getServiceStats_args(this);
    }

    public void setFieldValue(int fieldID, Object value) {
      switch (fieldID) {
      default:
        throw new IllegalArgumentException("Field " + fieldID + " doesn't exist!");
      }
    }

    public Object getFieldValue(int fieldID) {
      switch (fieldID) {
      default:
        throw new IllegalArgumentException("Field " + fieldID + " doesn't exist!");
      }
    }

    // Returns true if field corresponding to fieldID is set (has been asigned a value) and false otherwise
    public boolean isSet(int fieldID) {
      switch (fieldID) {
      default:
        throw new IllegalArgumentException("Field " + fieldID + " doesn't exist!");
      }
    }

    @Override
    public boolean equals(Object that) {
      if (that == null)
        return false;
      if (that instanceof getServiceStats_args)
        return this.equals((getServiceStats_args)that);
      return false;
    }

    public boolean equals(getServiceStats_args that) {
      if (that == null)
        return false;

      return true;
    }

    @Override
    public int hashCode() {
      return 0;
    }

    public void read(TProtocol iprot) throws TException {
      TField field;
      iprot.readStructBegin();
      while (true)
      {
        field = iprot.readFieldBegin();
        if (field.type == TType.STOP) { 
          break;
        }
        switch (field.id)
        {
          default:
            TProtocolUtil.skip(iprot, field.type);
            break;
        }
        iprot.readFieldEnd();
      }
      iprot.readStructEnd();


      // check for required fields of primitive type, which can't be checked in the validate method
      validate();
    }

    public void write(TProtocol oprot) throws TException {
      validate();

      oprot.writeStructBegin(STRUCT_DESC);
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

    @Override
    public String toString() {
      StringBuilder sb = new StringBuilder("getServiceStats_args(");
      boolean first = true;

      sb.append(")");
      return sb.toString();
    }

    public void validate() throws TException {
      // check for required fields
      // check that fields of type enum have valid values
    }

  }

  public static class getServiceStats_result implements TBase, java.io.Serializable, Cloneable   {
    private static final TStruct STRUCT_DESC = new TStruct("getServiceStats_result");
    private static final TField SUCCESS_FIELD_DESC = new TField("success", TType.MAP, (short)0);

    public Map<String,Long> success;
    public static final int SUCCESS = 0;

    private final Isset __isset = new Isset();
    private static final class Isset implements java.io.Serializable {
    }

    public static final Map<Integer, FieldMetaData> metaDataMap = Collections.unmodifiableMap(new HashMap<Integer, FieldMetaData>() {{
      put(SUCCESS, new FieldMetaData("success", TFieldRequirementType.DEFAULT, 
          new MapMetaData(TType.MAP, 
              new FieldValueMetaData(TType.STRING), 
              new FieldValueMetaData(TType.I64))));
    }});

    static {
      FieldMetaData.addStructMetaDataMap(getServiceStats_result.class, metaDataMap);
    }

    public getServiceStats_result() {
    }

    public getServiceStats_result(
      Map<String,Long> success)
    {
      this();
      this.success = success;
    }

    /**
     * Performs a deep copy on <i>other</i>.
     */
    public getServiceStats_result(getServiceStats_result other) {
      if (other.isSetSuccess()) {
        Map<String,Long> __this__success = new HashMap<String,Long>();
        for (Map.Entry<String, Long> other_element : other.success.entrySet()) {

          String other_element_key = other_element.getKey();
          Long other_element_value = other_element.getValue();

          String __this__success_copy_key = other_element_key;

          Long __this__success_copy_value = other_element_value;

          __this__success.put(__this__success_copy_key, __this__success_copy_value);
        }
        this.success = __this__success;
      }
    }

    @Override
    public getServiceStats_result clone() {
      return new getServiceStats_result(this);
    }

    public int getSuccessSize() {
      return (this.success == null) ? 0 : this.success.size();
    }

    public void putToSuccess(String key, long val) {
      if (this.success == null) {
        this.success = new HashMap<String,Long>();
      }
      this.success.put(key, val);
    }

    public Map<String,Long> getSuccess() {
      return this.success;
    }

    public void setSuccess(Map<String,Long> success) {
      this.success = success;
    }

    public void unsetSuccess() {
      this.success = null;
    }

    // Returns true if field success is set (has been asigned a value) and false otherwise
    public boolean isSetSuccess() {
      return this.success != null;
    }

    public void setSuccessIsSet(boolean value) {
      if (!value) {
        this.success = null;
      }
    }

    public void setFieldValue(int fieldID, Object value) {
      switch (fieldID) {
      case SUCCESS:
        if (value == null) {
          unsetSuccess();
        } else {
          setSuccess((Map<String,Long>)value);
        }
        break;

      default:
        throw new IllegalArgumentException("Field " + fieldID + " doesn't exist!");
      }
    }

    public Object getFieldValue(int fieldID) {
      switch (fieldID) {
      case SUCCESS:
        return getSuccess();

      default:
        throw new IllegalArgumentException("Field " + fieldID + " doesn't exist!");
      }
    }

    // Returns true if field corresponding to fieldID is set (has been asigned a value) and false otherwise
    public boolean isSet(int fieldID) {
      switch (fieldID) {
      case SUCCESS:
        return isSetSuccess();
      default:
        throw new IllegalArgumentException("Field " + fieldID + " doesn't exist!");
      }
    }

    @Override
    public boolean equals(Object that) {
      if (that == null)
        return false;
      if (that instanceof getServiceStats_result)
        return this.equals((getServiceStats_result)that);
      return false;
    }

    public boolean equals(getServiceStats_result that) {
      if (that == null)
        return false;

      boolean this_present_success = true && this.isSetSuccess();
      boolean that_present_success = true && that.isSetSuccess();
      if (this_present_success || that_present_success) {
        if (!(this_present_success && that_present_success))
          return false;
        if (!this.success.equals(that.success))
          return false;
      }

      return true;
    }

    @Override
    public int hashCode() {
      return 0;
    }

    public void read(TProtocol iprot) throws TException {
      TField field;
      iprot.readStructBegin();
      while (true)
      {
        field = iprot.readFieldBegin();
        if (field.type == TType.STOP) { 
          break;
        }
        switch (field.id)
        {
          case SUCCESS:
            if (field.type == TType.MAP) {
              {
                TMap _map0 = iprot.readMapBegin();
                this.success = new HashMap<String,Long>(2*_map0.size);
                for (int _i1 = 0; _i1 < _map0.size; ++_i1)
                {
                  String _key2;
                  long _val3;
                  _key2 = iprot.readString();
                  _val3 = iprot.readI64();
                  this.success.put(_key2, _val3);
                }
                iprot.readMapEnd();
              }
            } else { 
              TProtocolUtil.skip(iprot, field.type);
            }
            break;
          default:
            TProtocolUtil.skip(iprot, field.type);
            break;
        }
        iprot.readFieldEnd();
      }
      iprot.readStructEnd();


      // check for required fields of primitive type, which can't be checked in the validate method
      validate();
    }

    public void write(TProtocol oprot) throws TException {
      oprot.writeStructBegin(STRUCT_DESC);

      if (this.isSetSuccess()) {
        oprot.writeFieldBegin(SUCCESS_FIELD_DESC);
        {
          oprot.writeMapBegin(new TMap(TType.STRING, TType.I64, this.success.size()));
          for (Map.Entry<String, Long> _iter4 : this.success.entrySet())          {
            oprot.writeString(_iter4.getKey());
            oprot.writeI64(_iter4.getValue());
          }
          oprot.writeMapEnd();
        }
        oprot.writeFieldEnd();
      }
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

    @Override
    public String toString() {
      StringBuilder sb = new StringBuilder("getServiceStats_result(");
      boolean first = true;

      sb.append("success:");
      if (this.success == null) {
        sb.append("null");
      } else {
        sb.append(this.success);
      }
      first = false;
      sb.append(")");
      return sb.toString();
    }

    public void validate() throws TException {
      // check for required fields
      // check that fields of type enum have valid values
    }

  }

  public static class ping_args implements TBase, java.io.Serializable, Cloneable   {
    private static final TStruct STRUCT_DESC = new TStruct("ping_args");

    public static final Map<Integer, FieldMetaData> metaDataMap = Collections.unmodifiableMap(new HashMap<Integer, FieldMetaData>() {{
    }});

    static {
      FieldMetaData.addStructMetaDataMap(ping_args.class, metaDataMap);
    }

    public ping_args() {
    }

    /**
     * Performs a deep copy on <i>other</i>.
     */
    public ping_args(ping_args other) {
    }

    @Override
    public ping_args clone() {
      return new ping_args(this);
    }

    public void setFieldValue(int fieldID, Object value) {
      switch (fieldID) {
      default:
        throw new IllegalArgumentException("Field " + fieldID + " doesn't exist!");
      }
    }

    public Object getFieldValue(int fieldID) {
      switch (fieldID) {
      default:
        throw new IllegalArgumentException("Field " + fieldID + " doesn't exist!");
      }
    }

    // Returns true if field corresponding to fieldID is set (has been asigned a value) and false otherwise
    public boolean isSet(int fieldID) {
      switch (fieldID) {
      default:
        throw new IllegalArgumentException("Field " + fieldID + " doesn't exist!");
      }
    }

    @Override
    public boolean equals(Object that) {
      if (that == null)
        return false;
      if (that instanceof ping_args)
        return this.equals((ping_args)that);
      return false;
    }

    public boolean equals(ping_args that) {
      if (that == null)
        return false;

      return true;
    }

    @Override
    public int hashCode() {
      return 0;
    }

    public void read(TProtocol iprot) throws TException {
      TField field;
      iprot.readStructBegin();
      while (true)
      {
        field = iprot.readFieldBegin();
        if (field.type == TType.STOP) { 
          break;
        }
        switch (field.id)
        {
          default:
            TProtocolUtil.skip(iprot, field.type);
            break;
        }
        iprot.readFieldEnd();
      }
      iprot.readStructEnd();


      // check for required fields of primitive type, which can't be checked in the validate method
      validate();
    }

    public void write(TProtocol oprot) throws TException {
      validate();

      oprot.writeStructBegin(STRUCT_DESC);
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

    @Override
    public String toString() {
      StringBuilder sb = new StringBuilder("ping_args(");
      boolean first = true;

      sb.append(")");
      return sb.toString();
    }

    public void validate() throws TException {
      // check for required fields
      // check that fields of type enum have valid values
    }

  }

  public static class ping_result implements TBase, java.io.Serializable, Cloneable   {
    private static final TStruct STRUCT_DESC = new TStruct("ping_result");

    public static final Map<Integer, FieldMetaData> metaDataMap = Collections.unmodifiableMap(new HashMap<Integer, FieldMetaData>() {{
    }});

    static {
      FieldMetaData.addStructMetaDataMap(ping_result.class, metaDataMap);
    }

    public ping_result() {
    }

    /**
     * Performs a deep copy on <i>other</i>.
     */
    public ping_result(ping_result other) {
    }

    @Override
    public ping_result clone() {
      return new ping_result(this);
    }

    public void setFieldValue(int fieldID, Object value) {
      switch (fieldID) {
      default:
        throw new IllegalArgumentException("Field " + fieldID + " doesn't exist!");
      }
    }

    public Object getFieldValue(int fieldID) {
      switch (fieldID) {
      default:
        throw new IllegalArgumentException("Field " + fieldID + " doesn't exist!");
      }
    }

    // Returns true if field corresponding to fieldID is set (has been asigned a value) and false otherwise
    public boolean isSet(int fieldID) {
      switch (fieldID) {
      default:
        throw new IllegalArgumentException("Field " + fieldID + " doesn't exist!");
      }
    }

    @Override
    public boolean equals(Object that) {
      if (that == null)
        return false;
      if (that instanceof ping_result)
        return this.equals((ping_result)that);
      return false;
    }

    public boolean equals(ping_result that) {
      if (that == null)
        return false;

      return true;
    }

    @Override
    public int hashCode() {
      return 0;
    }

    public void read(TProtocol iprot) throws TException {
      TField field;
      iprot.readStructBegin();
      while (true)
      {
        field = iprot.readFieldBegin();
        if (field.type == TType.STOP) { 
          break;
        }
        switch (field.id)
        {
          default:
            TProtocolUtil.skip(iprot, field.type);
            break;
        }
        iprot.readFieldEnd();
      }
      iprot.readStructEnd();


      // check for required fields of primitive type, which can't be checked in the validate method
      validate();
    }

    public void write(TProtocol oprot) throws TException {
      oprot.writeStructBegin(STRUCT_DESC);

      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

    @Override
    public String toString() {
      StringBuilder sb = new StringBuilder("ping_result(");
      boolean first = true;

      sb.append(")");
      return sb.toString();
    }

    public void validate() throws TException {
      // check for required fields
      // check that fields of type enum have valid values
    }

  }

  public static class getLogFrom_args implements TBase, java.io.Serializable, Cloneable   {
    private static final TStruct STRUCT_DESC = new TStruct("getLogFrom_args");
    private static final TField LSN_FIELD_DESC = new TField("lsn", TType.STRING, (short)1);
    private static final TField KB_LIMIT_FIELD_DESC = new TField("kbLimit", TType.I32, (short)2);

    public String lsn;
    public static final int LSN = 1;
    public int kbLimit;
    public static final int KBLIMIT = 2;

    private final Isset __isset = new Isset();
    private static final class Isset implements java.io.Serializable {
      public boolean kbLimit = false;
    }

    public static final Map<Integer, FieldMetaData> metaDataMap = Collections.unmodifiableMap(new HashMap<Integer, FieldMetaData>() {{
      put(LSN, new FieldMetaData("lsn", TFieldRequirementType.DEFAULT, 
          new FieldValueMetaData(TType.STRING)));
      put(KBLIMIT, new FieldMetaData("kbLimit", TFieldRequirementType.DEFAULT, 
          new FieldValueMetaData(TType.I32)));
    }});

    static {
      FieldMetaData.addStructMetaDataMap(getLogFrom_args.class, metaDataMap);
    }

    public getLogFrom_args() {
    }

    public getLogFrom_args(
      String lsn,
      int kbLimit)
    {
      this();
      this.lsn = lsn;
      this.kbLimit = kbLimit;
      this.__isset.kbLimit = true;
    }

    /**
     * Performs a deep copy on <i>other</i>.
     */
    public getLogFrom_args(getLogFrom_args other) {
      if (other.isSetLsn()) {
        this.lsn = other.lsn;
      }
      __isset.kbLimit = other.__isset.kbLimit;
      this.kbLimit = other.kbLimit;
    }

    @Override
    public getLogFrom_args clone() {
      return new getLogFrom_args(this);
    }

    public String getLsn() {
      return this.lsn;
    }

    public void setLsn(String lsn) {
      this.lsn = lsn;
    }

    public void unsetLsn() {
      this.lsn = null;
    }

    // Returns true if field lsn is set (has been asigned a value) and false otherwise
    public boolean isSetLsn() {
      return this.lsn != null;
    }

    public void setLsnIsSet(boolean value) {
      if (!value) {
        this.lsn = null;
      }
    }

    public int getKbLimit() {
      return this.kbLimit;
    }

    public void setKbLimit(int kbLimit) {
      this.kbLimit = kbLimit;
      this.__isset.kbLimit = true;
    }

    public void unsetKbLimit() {
      this.__isset.kbLimit = false;
    }

    // Returns true if field kbLimit is set (has been asigned a value) and false otherwise
    public boolean isSetKbLimit() {
      return this.__isset.kbLimit;
    }

    public void setKbLimitIsSet(boolean value) {
      this.__isset.kbLimit = value;
    }

    public void setFieldValue(int fieldID, Object value) {
      switch (fieldID) {
      case LSN:
        if (value == null) {
          unsetLsn();
        } else {
          setLsn((String)value);
        }
        break;

      case KBLIMIT:
        if (value == null) {
          unsetKbLimit();
        } else {
          setKbLimit((Integer)value);
        }
        break;

      default:
        throw new IllegalArgumentException("Field " + fieldID + " doesn't exist!");
      }
    }

    public Object getFieldValue(int fieldID) {
      switch (fieldID) {
      case LSN:
        return getLsn();

      case KBLIMIT:
        return new Integer(getKbLimit());

      default:
        throw new IllegalArgumentException("Field " + fieldID + " doesn't exist!");
      }
    }

    // Returns true if field corresponding to fieldID is set (has been asigned a value) and false otherwise
    public boolean isSet(int fieldID) {
      switch (fieldID) {
      case LSN:
        return isSetLsn();
      case KBLIMIT:
        return isSetKbLimit();
      default:
        throw new IllegalArgumentException("Field " + fieldID + " doesn't exist!");
      }
    }

    @Override
    public boolean equals(Object that) {
      if (that == null)
        return false;
      if (that instanceof getLogFrom_args)
        return this.equals((getLogFrom_args)that);
      return false;
    }

    public boolean equals(getLogFrom_args that) {
      if (that == null)
        return false;

      boolean this_present_lsn = true && this.isSetLsn();
      boolean that_present_lsn = true && that.isSetLsn();
      if (this_present_lsn || that_present_lsn) {
        if (!(this_present_lsn && that_present_lsn))
          return false;
        if (!this.lsn.equals(that.lsn))
          return false;
      }

      boolean this_present_kbLimit = true;
      boolean that_present_kbLimit = true;
      if (this_present_kbLimit || that_present_kbLimit) {
        if (!(this_present_kbLimit && that_present_kbLimit))
          return false;
        if (this.kbLimit != that.kbLimit)
          return false;
      }

      return true;
    }

    @Override
    public int hashCode() {
      return 0;
    }

    public void read(TProtocol iprot) throws TException {
      TField field;
      iprot.readStructBegin();
      while (true)
      {
        field = iprot.readFieldBegin();
        if (field.type == TType.STOP) { 
          break;
        }
        switch (field.id)
        {
          case LSN:
            if (field.type == TType.STRING) {
              this.lsn = iprot.readString();
            } else { 
              TProtocolUtil.skip(iprot, field.type);
            }
            break;
          case KBLIMIT:
            if (field.type == TType.I32) {
              this.kbLimit = iprot.readI32();
              this.__isset.kbLimit = true;
            } else { 
              TProtocolUtil.skip(iprot, field.type);
            }
            break;
          default:
            TProtocolUtil.skip(iprot, field.type);
            break;
        }
        iprot.readFieldEnd();
      }
      iprot.readStructEnd();


      // check for required fields of primitive type, which can't be checked in the validate method
      validate();
    }

    public void write(TProtocol oprot) throws TException {
      validate();

      oprot.writeStructBegin(STRUCT_DESC);
      if (this.lsn != null) {
        oprot.writeFieldBegin(LSN_FIELD_DESC);
        oprot.writeString(this.lsn);
        oprot.writeFieldEnd();
      }
      oprot.writeFieldBegin(KB_LIMIT_FIELD_DESC);
      oprot.writeI32(this.kbLimit);
      oprot.writeFieldEnd();
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

    @Override
    public String toString() {
      StringBuilder sb = new StringBuilder("getLogFrom_args(");
      boolean first = true;

      sb.append("lsn:");
      if (this.lsn == null) {
        sb.append("null");
      } else {
        sb.append(this.lsn);
      }
      first = false;
      if (!first) sb.append(", ");
      sb.append("kbLimit:");
      sb.append(this.kbLimit);
      first = false;
      sb.append(")");
      return sb.toString();
    }

    public void validate() throws TException {
      // check for required fields
      // check that fields of type enum have valid values
    }

  }

  public static class getLogFrom_result implements TBase, java.io.Serializable, Cloneable   {
    private static final TStruct STRUCT_DESC = new TStruct("getLogFrom_result");
    private static final TField SUCCESS_FIELD_DESC = new TField("success", TType.LIST, (short)0);

    public List<logEntry> success;
    public static final int SUCCESS = 0;

    private final Isset __isset = new Isset();
    private static final class Isset implements java.io.Serializable {
    }

    public static final Map<Integer, FieldMetaData> metaDataMap = Collections.unmodifiableMap(new HashMap<Integer, FieldMetaData>() {{
      put(SUCCESS, new FieldMetaData("success", TFieldRequirementType.DEFAULT, 
          new ListMetaData(TType.LIST, 
              new StructMetaData(TType.STRUCT, logEntry.class))));
    }});

    static {
      FieldMetaData.addStructMetaDataMap(getLogFrom_result.class, metaDataMap);
    }

    public getLogFrom_result() {
    }

    public getLogFrom_result(
      List<logEntry> success)
    {
      this();
      this.success = success;
    }

    /**
     * Performs a deep copy on <i>other</i>.
     */
    public getLogFrom_result(getLogFrom_result other) {
      if (other.isSetSuccess()) {
        List<logEntry> __this__success = new ArrayList<logEntry>();
        for (logEntry other_element : other.success) {
          __this__success.add(new logEntry(other_element));
        }
        this.success = __this__success;
      }
    }

    @Override
    public getLogFrom_result clone() {
      return new getLogFrom_result(this);
    }

    public int getSuccessSize() {
      return (this.success == null) ? 0 : this.success.size();
    }

    public java.util.Iterator<logEntry> getSuccessIterator() {
      return (this.success == null) ? null : this.success.iterator();
    }

    public void addToSuccess(logEntry elem) {
      if (this.success == null) {
        this.success = new ArrayList<logEntry>();
      }
      this.success.add(elem);
    }

    public List<logEntry> getSuccess() {
      return this.success;
    }

    public void setSuccess(List<logEntry> success) {
      this.success = success;
    }

    public void unsetSuccess() {
      this.success = null;
    }

    // Returns true if field success is set (has been asigned a value) and false otherwise
    public boolean isSetSuccess() {
      return this.success != null;
    }

    public void setSuccessIsSet(boolean value) {
      if (!value) {
        this.success = null;
      }
    }

    public void setFieldValue(int fieldID, Object value) {
      switch (fieldID) {
      case SUCCESS:
        if (value == null) {
          unsetSuccess();
        } else {
          setSuccess((List<logEntry>)value);
        }
        break;

      default:
        throw new IllegalArgumentException("Field " + fieldID + " doesn't exist!");
      }
    }

    public Object getFieldValue(int fieldID) {
      switch (fieldID) {
      case SUCCESS:
        return getSuccess();

      default:
        throw new IllegalArgumentException("Field " + fieldID + " doesn't exist!");
      }
    }

    // Returns true if field corresponding to fieldID is set (has been asigned a value) and false otherwise
    public boolean isSet(int fieldID) {
      switch (fieldID) {
      case SUCCESS:
        return isSetSuccess();
      default:
        throw new IllegalArgumentException("Field " + fieldID + " doesn't exist!");
      }
    }

    @Override
    public boolean equals(Object that) {
      if (that == null)
        return false;
      if (that instanceof getLogFrom_result)
        return this.equals((getLogFrom_result)that);
      return false;
    }

    public boolean equals(getLogFrom_result that) {
      if (that == null)
        return false;

      boolean this_present_success = true && this.isSetSuccess();
      boolean that_present_success = true && that.isSetSuccess();
      if (this_present_success || that_present_success) {
        if (!(this_present_success && that_present_success))
          return false;
        if (!this.success.equals(that.success))
          return false;
      }

      return true;
    }

    @Override
    public int hashCode() {
      return 0;
    }

    public void read(TProtocol iprot) throws TException {
      TField field;
      iprot.readStructBegin();
      while (true)
      {
        field = iprot.readFieldBegin();
        if (field.type == TType.STOP) { 
          break;
        }
        switch (field.id)
        {
          case SUCCESS:
            if (field.type == TType.LIST) {
              {
                TList _list5 = iprot.readListBegin();
                this.success = new ArrayList<logEntry>(_list5.size);
                for (int _i6 = 0; _i6 < _list5.size; ++_i6)
                {
                  logEntry _elem7;
                  _elem7 = new logEntry();
                  _elem7.read(iprot);
                  this.success.add(_elem7);
                }
                iprot.readListEnd();
              }
            } else { 
              TProtocolUtil.skip(iprot, field.type);
            }
            break;
          default:
            TProtocolUtil.skip(iprot, field.type);
            break;
        }
        iprot.readFieldEnd();
      }
      iprot.readStructEnd();


      // check for required fields of primitive type, which can't be checked in the validate method
      validate();
    }

    public void write(TProtocol oprot) throws TException {
      oprot.writeStructBegin(STRUCT_DESC);

      if (this.isSetSuccess()) {
        oprot.writeFieldBegin(SUCCESS_FIELD_DESC);
        {
          oprot.writeListBegin(new TList(TType.STRUCT, this.success.size()));
          for (logEntry _iter8 : this.success)          {
            _iter8.write(oprot);
          }
          oprot.writeListEnd();
        }
        oprot.writeFieldEnd();
      }
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

    @Override
    public String toString() {
      StringBuilder sb = new StringBuilder("getLogFrom_result(");
      boolean first = true;

      sb.append("success:");
      if (this.success == null) {
        sb.append("null");
      } else {
        sb.append(this.success);
      }
      first = false;
      sb.append(")");
      return sb.toString();
    }

    public void validate() throws TException {
      // check for required fields
      // check that fields of type enum have valid values
    }

  }

}
