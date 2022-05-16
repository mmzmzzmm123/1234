package work.cib.xml_socket;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

public class TestVo implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	private Req_TestVo_msg req_TestVo_msg;
	private Resp_TestVo_msg resp_TestVo_msg;
	private Exp_TestVo_msg exp_TestVo_msg;

	@XmlAccessorType(XmlAccessType.FIELD)
	@XmlType(name = "", propOrder = { "req" })
	@XmlRootElement(name = "cap4j")
	public static class Req_TestVo_msg implements java.io.Serializable {
		private static final long serialVersionUID = 1L;
		@XmlElement(name = "req", required = true)
		private Req req;
		public Req getReq() {
			return req;
		}
		public void setReq(Req req) {
			this.req = req;
		}

		@XmlAccessorType(XmlAccessType.FIELD)
		@XmlType(name = "", propOrder = { "head", "body" })
		@XmlRootElement(name = "req")
		public static class Req implements java.io.Serializable {
			private static final long serialVersionUID = 1L;
			@XmlElement(name = "head", required = true)
			private Head head;
			@XmlElement(name = "body", required = true)
			private Body body;

			public Head getHead() {
				return head;
			}
			public void setHead(Head head) {
				this.head = head;
			}
			public Body getBody() {
				return body;
			}
			public void setBody(Body body) {
				this.body = body;
			}

			@XmlAccessorType(XmlAccessType.FIELD)
			@XmlType(name = "", propOrder = { "jydm", "zddh", "jrxtbh",
					"jrxtmc", "ywlx", "qtrq", "qtjylsxh", "dqdh", "jygy",
					"jyjg", "tdlx", "scwjbz", "scwjm", "scwjlj" })
			@XmlRootElement(name = "head")
			public static class Head implements java.io.Serializable {
				private static final long serialVersionUID = 1L;
				@XmlElement(name = "jydm", required = true)
				private String jydm;
				@XmlElement(name = "zddh", required = true)
				private String zddh;
				@XmlElement(name = "jrxtbh", required = true)
				private String jrxtbh;
				@XmlElement(name = "jrxtmc", required = true)
				private String jrxtmc;
				@XmlElement(name = "ywlx", required = true)
				private String ywlx;
				@XmlElement(name = "qtrq", required = true)
				private String qtrq;
				@XmlElement(name = "qtjylsxh", required = true)
				private String qtjylsxh;
				@XmlElement(name = "dqdh", required = true)
				private String dqdh;
				@XmlElement(name = "jygy", required = true)
				private String jygy;
				@XmlElement(name = "jyjg", required = true)
				private String jyjg;
				@XmlElement(name = "tdlx", required = true)
				private String tdlx;
				@XmlElement(name = "scwjbz", required = true)
				private String scwjbz;
				@XmlElement(name = "scwjm", required = true)
				private String scwjm;
				@XmlElement(name = "scwjlj", required = true)
				private String scwjlj;

				public String getJydm() {
					return jydm;
				}
				public void setJydm(String jydm) {
					this.jydm = jydm;
				}
				public String getZddh() {
					return zddh;
				}
				public void setZddh(String zddh) {
					this.zddh = zddh;
				}
				public String getJrxtbh() {
					return jrxtbh;
				}
				public void setJrxtbh(String jrxtbh) {
					this.jrxtbh = jrxtbh;
				}
				public String getJrxtmc() {
					return jrxtmc;
				}
				public void setJrxtmc(String jrxtmc) {
					this.jrxtmc = jrxtmc;
				}
				public String getYwlx() {
					return ywlx;
				}
				public void setYwlx(String ywlx) {
					this.ywlx = ywlx;
				}
				public String getQtrq() {
					return qtrq;
				}
				public void setQtrq(String qtrq) {
					this.qtrq = qtrq;
				}
				public String getQtjylsxh() {
					return qtjylsxh;
				}
				public void setQtjylsxh(String qtjylsxh) {
					this.qtjylsxh = qtjylsxh;
				}
				public String getDqdh() {
					return dqdh;
				}
				public void setDqdh(String dqdh) {
					this.dqdh = dqdh;
				}
				public String getJygy() {
					return jygy;
				}
				public void setJygy(String jygy) {
					this.jygy = jygy;
				}
				public String getJyjg() {
					return jyjg;
				}
				public void setJyjg(String jyjg) {
					this.jyjg = jyjg;
				}
				public String getTdlx() {
					return tdlx;
				}
				public void setTdlx(String tdlx) {
					this.tdlx = tdlx;
				}
				public String getScwjbz() {
					return scwjbz;
				}
				public void setScwjbz(String scwjbz) {
					this.scwjbz = scwjbz;
				}
				public String getScwjm() {
					return scwjm;
				}
				public void setScwjm(String scwjm) {
					this.scwjm = scwjm;
				}
				public String getScwjlj() {
					return scwjlj;
				}
				public void setScwjlj(String scwjlj) {
					this.scwjlj = scwjlj;
				}
			}

			@XmlAccessorType(XmlAccessType.FIELD)
			@XmlType(name = "", propOrder = { "sjhm", "sjyzbz", "dxfsnr" })
			@XmlRootElement(name = "body")
			public static class Body implements java.io.Serializable {
				private static final long serialVersionUID = 1L;
				@XmlElement(name = "sjhm", required = true)
				private String sjhm;
				@XmlElement(name = "sjyzbz", required = true)
				private String sjyzbz;
				@XmlElement(name = "dxfsnr", required = true)
				private String dxfsnr;

				public String getSjhm() {
					return sjhm;
				}
				public void setSjhm(String sjhm) {
					this.sjhm = sjhm;
				}
				public String getSjyzbz() {
					return sjyzbz;
				}
				public void setSjyzbz(String sjyzbz) {
					this.sjyzbz = sjyzbz;
				}
				public String getDxfsnr() {
					return dxfsnr;
				}
				public void setDxfsnr(String dxfsnr) {
					this.dxfsnr = dxfsnr;
				}

			}
		}
	}

	@XmlAccessorType(XmlAccessType.FIELD)
	@XmlType(name = "", propOrder = { "resp" })
	@XmlRootElement(name = "cap4j")
	public static class Resp_TestVo_msg implements java.io.Serializable {
		private static final long serialVersionUID = 1L;
		@XmlElement(name = "resp", required = true)
		private Resp resp;
		public Resp getResp() {
			return resp;
		}
		public void setResp(Resp resp) {
			this.resp = resp;
		}

		@XmlAccessorType(XmlAccessType.FIELD)
		@XmlType(name = "", propOrder = { "head", "body" })
		@XmlRootElement(name = "resp")
		public static class Resp implements java.io.Serializable {

			private static final long serialVersionUID = 1L;
			@XmlElement(name = "head", required = true)
			private Head head;
			@XmlElement(name = "body", required = true)
			private Body body;

			public Head getHead() {
				return head;
			}
			public void setHead(Head head) {
				this.head = head;
			}
			public Body getBody() {
				return body;
			}
			public void setBody(Body body) {
				this.body = body;
			}

			@XmlAccessorType(XmlAccessType.FIELD)
			@XmlType(name = "", propOrder = { "respcode", "respmsg", "zhqzlsh",
					"wjbz", "wjmc" })
			@XmlRootElement(name = "head")
			public static class Head implements java.io.Serializable {
				private static final long serialVersionUID = 1L;
				@XmlElement(name = "respcode", required = true)
				private String respcode;
				@XmlElement(name = "respmsg", required = true)
				private String respmsg;
				@XmlElement(name = "zhqzlsh", required = true)
				private String zhqzlsh;
				@XmlElement(name = "wjbz", required = true)
				private String wjbz;
				@XmlElement(name = "wjmc", required = true)
				private String wjmc;

				public String getRespcode() {
					return respcode;
				}
				public void setRespcode(String respcode) {
					this.respcode = respcode;
				}
				public String getRespmsg() {
					return respmsg;
				}
				public void setRespmsg(String respmsg) {
					this.respmsg = respmsg;
				}
				public String getZhqzlsh() {
					return zhqzlsh;
				}
				public void setZhqzlsh(String zhqzlsh) {
					this.zhqzlsh = zhqzlsh;
				}
				public String getWjbz() {
					return wjbz;
				}
				public void setWjbz(String wjbz) {
					this.wjbz = wjbz;
				}
				public String getWjmc() {
					return wjmc;
				}
				public void setWjmc(String wjmc) {
					this.wjmc = wjmc;
				}

			}

			@XmlAccessorType(XmlAccessType.FIELD)
			@XmlType(name = "", propOrder = { "zhmc", "zhye" })
			@XmlRootElement(name = "head")
			public static class Body implements java.io.Serializable {
				private static final long serialVersionUID = 1L;
				@XmlElement(name = "zhmc", required = true)
				private String zhmc;
				@XmlElement(name = "zhye", required = true)
				private String zhye;

				public String getZhye() {
					return zhye;
				}
				public void setZhye(String zhye) {
					this.zhye = zhye;
				}
				public String getZhmc() {
					return zhmc;
				}
				public void setZhmc(String zhmc) {
					this.zhmc = zhmc;
				}

			}
		}
	}

	@XmlAccessorType(XmlAccessType.FIELD)
	@XmlType(name = "", propOrder = { "resp" })
	@XmlRootElement(name = "cap4j")
	public static class Exp_TestVo_msg implements java.io.Serializable {
		private static final long serialVersionUID = 1L;
		@XmlElement(name = "resp", required = true)
		private Resp resp;
		public Resp getResp() {
			return resp;
		}
		public void setResp(Resp resp) {
			this.resp = resp;
		}

		@XmlAccessorType(XmlAccessType.FIELD)
		@XmlType(name = "", propOrder = { "head" })
		@XmlRootElement(name = "resp")
		public static class Resp implements java.io.Serializable {
			private static final long serialVersionUID = 1L;
			@XmlElement(name = "head", required = true)
			private Head head;
			public Head getHead() {
				return head;
			}
			public void setHead(Head head) {
				this.head = head;
			}

			@XmlAccessorType(XmlAccessType.FIELD)
			@XmlType(name = "", propOrder = { "respcode", "respmsg" })
			@XmlRootElement(name = "head")
			public static class Head implements java.io.Serializable {
				private static final long serialVersionUID = 1L;
				@XmlElement(name = "respcode", required = true)
				private String respcode;
				@XmlElement(name = "respmsg", required = true)
				private String respmsg;

				public String getRespcode() {
					return respcode;
				}
				public void setRespcode(String respcode) {
					this.respcode = respcode;
				}
				public String getRespmsg() {
					return respmsg;
				}
				public void setRespmsg(String respmsg) {
					this.respmsg = respmsg;
				}
			}
		}
	}
	//--------------------------------------
	public Req_TestVo_msg getReq_TestVo_msg() {
		return req_TestVo_msg;
	}
	public void setReq_TestVo_msg(Req_TestVo_msg req_TestVo_msg) {
		this.req_TestVo_msg = req_TestVo_msg;
	}
	public Resp_TestVo_msg getResp_TestVo_msg() {
		return resp_TestVo_msg;
	}
	public void setResp_TestVo_msg(Resp_TestVo_msg resp_TestVo_msg) {
		this.resp_TestVo_msg = resp_TestVo_msg;
	}
	public Exp_TestVo_msg getExp_TestVo_msg() {
		return exp_TestVo_msg;
	}
	public void setExp_TestVo_msg(Exp_TestVo_msg exp_TestVo_msg) {
		this.exp_TestVo_msg = exp_TestVo_msg;
	}
}
