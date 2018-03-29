package com.bnmyni.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HtmlSnatchUtil01 {

	public static void main(String[] args) throws Exception {
		List<Map<String, String>>  list = HtmlSnatchUtil01.listTmps();
		for(Map<String, String> tmpMap :list ){
			String text = tmpMap.get("text");
			String href = tmpMap.get("href");
			long time0 = System.currentTimeMillis();
			download3("E:\\liyongle\\"+text+".mp4"  , href);
			long time1 = System.currentTimeMillis();
			System.out.println(text +".mp4  下载完成,耗时："+(time1-time0));
		}
		System.exit(0);
	}

	private static List<Map<String, String>> listTmps(){
		List<Map<String, String>> listTmps = new ArrayList<Map<String, String>>();
		Map<String, String> tmpMap = new HashMap<String, String>();
		tmpMap.put("text", "补发：李老师数学复习全系列之序章1（高中数学的学习方法）");
		tmpMap.put("href", "http://120.198.235.230/ugcyd.qq.com/p0518u9zv96.mp4");
		listTmps.add(tmpMap);
		tmpMap = new HashMap<String, String>();
		tmpMap.put("text", "补发：李老师数学复习全系列之序章2（高中数学的学习方法）");
		tmpMap.put("href", "http://120.198.235.230/ugcyd.qq.com/q0518apnvhb.mp4");
		listTmps.add(tmpMap);
		tmpMap = new HashMap<String, String>();
		tmpMap.put("text", "李老师数学全系列第1讲：集合串讲");
		tmpMap.put("href", "http://120.198.235.230/ugcyd.qq.com/u0518fw5jpm.mp4");
		listTmps.add(tmpMap);
		tmpMap = new HashMap<String, String>();
		tmpMap.put("text", "李老师数学全系列第2讲：指数函数与对数函数总结");
		tmpMap.put("href", "http://120.198.235.230/ugcyd.qq.com/v0518vf6kje.mp4");
		listTmps.add(tmpMap);
		tmpMap = new HashMap<String, String>();
		tmpMap.put("text", "李老师高三数学复习全系列第3讲：指数函数与对数函数习题课");
		tmpMap.put("href", "http://120.198.235.230/ugcyd.qq.com/b0519xiqszr.mp4");
		listTmps.add(tmpMap);
		tmpMap = new HashMap<String, String>();
		tmpMap.put("text", "李老师数学复习全系列第4讲：指数与对数的比大小问题");
		tmpMap.put("href", "http://120.198.235.230/ugcyd.qq.com/r0520om6nwk.mp4");
		listTmps.add(tmpMap);
		tmpMap = new HashMap<String, String>();
		tmpMap.put("text", "李老师数学复习全系列第5讲：图像法解方程，函数平移方法论");
		tmpMap.put("href", "http://120.198.235.230/ugcyd.qq.com/j0520gg5buc.mp4");
		listTmps.add(tmpMap);
		tmpMap = new HashMap<String, String>();
		tmpMap.put("text", "李老师数学复习全系列第6讲：函数值域的求法1");
		tmpMap.put("href", "http://120.198.235.230/ugcyd.qq.com/n0521hui98n.mp4");
		listTmps.add(tmpMap);
		tmpMap = new HashMap<String, String>();
		tmpMap.put("text", "李老师数学复习全系列第7讲：函数值域的求法2");
		tmpMap.put("href", "http://120.198.235.230/ugcyd.qq.com/y0521lxxxrx.mp4");
		listTmps.add(tmpMap);
		tmpMap = new HashMap<String, String>();
		tmpMap.put("text", "李老师高三数学全系列第8讲：函数值域的求法3");
		tmpMap.put("href", "http://120.198.235.230/ugcyd.qq.com/x05220b5z2i.mp4");
		listTmps.add(tmpMap);
		tmpMap = new HashMap<String, String>();
		tmpMap.put("text", "李老师高三数学全系列第9讲：函数值域的求法4");
		tmpMap.put("href", "http://120.198.235.230/ugcyd.qq.com/c0522bit8j5.mp4");
		listTmps.add(tmpMap);
		tmpMap = new HashMap<String, String>();
		tmpMap.put("text", "李老师高三数学全系列第10讲：含参等式1");
		tmpMap.put("href", "http://120.198.235.230/ugcyd.qq.com/y052244t5bu.mp4");
		listTmps.add(tmpMap);
		tmpMap = new HashMap<String, String>();
		tmpMap.put("text", "《高三数学60讲》第11期：含参不等式的局咋破 梦见自己睡觉是啥体验");
		tmpMap.put("href", "http://120.198.235.230/ugcyd.qq.com/g0523hkv9l9.mp4");
		listTmps.add(tmpMap);
		tmpMap = new HashMap<String, String>();
		tmpMap.put("text", "《高三数学60讲》第12期：函数切斜 数学家灭火逻辑");
		tmpMap.put("href", "http://120.198.235.230/ugcyd.qq.com/j05230alido.mp4");
		listTmps.add(tmpMap);
		tmpMap = new HashMap<String, String>();
		tmpMap.put("text", "《高三数学60讲》：函数对称性");
		tmpMap.put("href", "http://120.198.235.230/ugcyd.qq.com/d0523qlnzqx.mp4");
		listTmps.add(tmpMap);
		tmpMap = new HashMap<String, String>();
		tmpMap.put("text", "《高三数学60讲》：函数奇偶性");
		tmpMap.put("href", "http://120.198.235.230/ugcyd.qq.com/j0524mt8r2n.mp4");
		listTmps.add(tmpMap);
		tmpMap = new HashMap<String, String>();
		tmpMap.put("text", "《高三数学60讲》：函数周期性");
		tmpMap.put("href", "http://120.198.235.230/ugcyd.qq.com/a0525blu7h5.mp4");
		listTmps.add(tmpMap);
		tmpMap = new HashMap<String, String>();
		tmpMap.put("text", "《高三数学60讲》: 函数性质综合");
		tmpMap.put("href", "http://120.198.235.230/ugcyd.qq.com/k0525xhocja.mp4");
		listTmps.add(tmpMap);
		tmpMap = new HashMap<String, String>();
		tmpMap.put("text", "《高三数学60讲》：任意角的三角函数");
		tmpMap.put("href", "http://120.198.235.230/ugcyd.qq.com/c0526syb6ya.mp4");
		listTmps.add(tmpMap);
		tmpMap = new HashMap<String, String>();
		tmpMap.put("text", "《高三数学60讲》：三角函数的定义");
		tmpMap.put("href", "http://120.198.235.230/ugcyd.qq.com/j0526a64niq.mp4");
		listTmps.add(tmpMap);
		tmpMap = new HashMap<String, String>();
		tmpMap.put("text", "《高三数学60讲》：三角函数的图像");
		tmpMap.put("href", "http://120.198.235.230/ugcyd.qq.com/e0527chj2xt.mp4");
		listTmps.add(tmpMap);
		tmpMap = new HashMap<String, String>();
		tmpMap.put("text", "《高三数学60讲》：三角函数的变换");
		tmpMap.put("href", "http://120.198.235.230/ugcyd.qq.com/x05270gmozk.mp4");
		listTmps.add(tmpMap);
		tmpMap = new HashMap<String, String>();
		tmpMap.put("text", "《高三数学60讲》：三角函数公式");
		tmpMap.put("href", "http://120.198.235.230/ugcyd.qq.com/a0528zqrrht.mp4");
		listTmps.add(tmpMap);
		tmpMap = new HashMap<String, String>();
		tmpMap.put("text", "《高三数学60讲》：三角函数的化简");
		tmpMap.put("href", "http://120.198.235.230/ugcyd.qq.com/g0529g4mst5.mp4");
		listTmps.add(tmpMap);
		tmpMap = new HashMap<String, String>();
		tmpMap.put("text", "《高三数学60讲》：含有三角函数的分式化简");
		tmpMap.put("href", "http://120.198.235.230/ugcyd.qq.com/w0529jbflrb.mp4");
		listTmps.add(tmpMap);
		tmpMap = new HashMap<String, String>();
		tmpMap.put("text", "《高三数学60讲》：三角形的那些“心”");
		tmpMap.put("href", "http://120.198.235.230/ugcyd.qq.com/c05306iw1my.mp4");
		listTmps.add(tmpMap);
		tmpMap = new HashMap<String, String>();
		tmpMap.put("text", "《高三数学60讲》：解三角形");
		tmpMap.put("href", "http://120.198.235.230/ugcyd.qq.com/p0531d1rgt0.mp4");
		listTmps.add(tmpMap);
		tmpMap = new HashMap<String, String>();
		tmpMap.put("text", "《高三数学60讲》：解三角形");
		tmpMap.put("href", "http://120.198.235.230/ugcyd.qq.com/f0532mk0t6v.mp4");
		listTmps.add(tmpMap);
		tmpMap = new HashMap<String, String>();
		tmpMap.put("text", "《高三数学60讲》：解三角形");
		tmpMap.put("href", "http://120.198.235.230/ugcyd.qq.com/j05324h3b1t.mp4");
		listTmps.add(tmpMap);
		tmpMap = new HashMap<String, String>();
		tmpMap.put("text", "《高三数学60讲》：平面向量知识点总结");
		tmpMap.put("href", "http://120.198.235.230/ugcyd.qq.com/b0533hbpkcs.mp4");
		listTmps.add(tmpMap);
		tmpMap = new HashMap<String, String>();
		tmpMap.put("text", "《高三数学60讲》：向量的平行");
		tmpMap.put("href", "http://120.198.235.230/ugcyd.qq.com/f053415s7jj.mp4");
		listTmps.add(tmpMap);
		tmpMap = new HashMap<String, String>();
		tmpMap.put("text", "《高三数学60讲》：平面向量的共线");
		tmpMap.put("href", "http://120.198.235.230/ugcyd.qq.com/p0535opnhgu.mp4");
		listTmps.add(tmpMap);
		tmpMap = new HashMap<String, String>();
		tmpMap.put("text", "《高三数学60讲》：向量的模与夹角");
		tmpMap.put("href", "http://120.198.235.230/ugcyd.qq.com/u0535z0gxk2.mp4");
		listTmps.add(tmpMap);
		tmpMap = new HashMap<String, String>();
		tmpMap.put("text", "《高三数学60讲》：平面向量的综合运用 向量模的计算");
		tmpMap.put("href", "http://120.198.235.230/ugcyd.qq.com/v0536rknc0v.mp4");
		listTmps.add(tmpMap);
		tmpMap = new HashMap<String, String>();
		tmpMap.put("text", "《高三数学60讲》：平面向量的综合运用 平面向量的运算");
		tmpMap.put("href", "http://120.198.235.230/ugcyd.qq.com/a0537qzgw78.mp4");
		listTmps.add(tmpMap);
		tmpMap = new HashMap<String, String>();
		tmpMap.put("text", "《高三数学60讲》：向量运算的综合练习1");
		tmpMap.put("href", "http://120.198.235.230/ugcyd.qq.com/q0538u5iq43.mp4");
		listTmps.add(tmpMap);
		tmpMap = new HashMap<String, String>();
		tmpMap.put("text", "《高三数学60讲》：向量运算的综合练习2");
		tmpMap.put("href", "http://120.198.235.230/ugcyd.qq.com/n0539yw46om.mp4");
		listTmps.add(tmpMap);
		tmpMap = new HashMap<String, String>();
		tmpMap.put("text", "《高三数学60讲》：向量的分解1");
		tmpMap.put("href", "http://120.198.235.230/ugcyd.qq.com/w0540v81qx5.mp4");
		listTmps.add(tmpMap);
		tmpMap = new HashMap<String, String>();
		tmpMap.put("text", "《高三数学60讲》：向量的分解2");
		tmpMap.put("href", "http://120.198.235.230/ugcyd.qq.com/e0540yk267z.mp4");
		listTmps.add(tmpMap);
		tmpMap = new HashMap<String, String>();
		tmpMap.put("text", "《高三数学60讲》:函数与三角函数复习课1");
		tmpMap.put("href", "http://120.198.235.230/ugcyd.qq.com/k0541k3jcac.mp4");
		listTmps.add(tmpMap);
		tmpMap = new HashMap<String, String>();
		tmpMap.put("text", "《高三数学60讲》:函数与三角函数复习课2");
		tmpMap.put("href", "http://120.198.235.230/ugcyd.qq.com/e0542lh96kd.mp4");
		listTmps.add(tmpMap);
		tmpMap = new HashMap<String, String>();
		tmpMap.put("text", "《高三数学60讲》:函数与三角函数复习课3");
		tmpMap.put("href", "http://120.198.235.230/ugcyd.qq.com/g0543owzd0n.mp4");
		listTmps.add(tmpMap);
		tmpMap = new HashMap<String, String>();
		tmpMap.put("text", "《高三数学60讲》:函数与三角函数复习课4");
		tmpMap.put("href", "http://120.198.235.230/ugcyd.qq.com/u05435puxr7.mp4");
		listTmps.add(tmpMap);
		tmpMap = new HashMap<String, String>();
		tmpMap.put("text", "《高三数学60讲》:函数与三角函数复习课5");
		tmpMap.put("href", "http://120.198.235.230/ugcyd.qq.com/k0544zgox7p.mp4");
		listTmps.add(tmpMap);
		tmpMap = new HashMap<String, String>();
		tmpMap.put("text", "《高三数学60讲第1期完结篇》:函数与三角函数复习课6");
		tmpMap.put("href", "http://120.198.235.230/ugcyd.qq.com/e0545g4xl0z.mp4");
		listTmps.add(tmpMap);
		tmpMap = new HashMap<String, String>();
		tmpMap.put("text", "《高三数学60讲第2期》：直线1");
		tmpMap.put("href", "http://120.198.235.230/ugcyd.qq.com/w0546zwp4rr.mp4");
		listTmps.add(tmpMap);
		tmpMap = new HashMap<String, String>();
		tmpMap.put("text", "《高三数学60讲第2期》：直线2");
		tmpMap.put("href", "http://120.198.235.230/ugcyd.qq.com/j05476jgtzf.mp4");
		listTmps.add(tmpMap);
		tmpMap = new HashMap<String, String>();
		tmpMap.put("text", "《高三数学60讲第2期》：直线3");
		tmpMap.put("href", "http://120.198.235.230/ugcyd.qq.com/z0548fksixk.mp4");
		listTmps.add(tmpMap);
		tmpMap = new HashMap<String, String>();
		tmpMap.put("text", "《高三数学60讲第2期》：直线4");
		tmpMap.put("href", "http://120.198.235.230/ugcyd.qq.com/l0549r0my45.mp4");
		listTmps.add(tmpMap);
		tmpMap = new HashMap<String, String>();
		tmpMap.put("text", "《高三数学60讲第2期》：直线5");
		tmpMap.put("href", "http://120.198.235.230/ugcyd.qq.com/b0550frh8nx.mp4");
		listTmps.add(tmpMap);
		tmpMap = new HashMap<String, String>();
		tmpMap.put("text", "《高三数学60讲第2期》：圆1");
		tmpMap.put("href", "http://120.198.235.230/ugcyd.qq.com/m0551q0argt.mp4");
		listTmps.add(tmpMap);
		tmpMap = new HashMap<String, String>();
		tmpMap.put("text", "《高三数学60讲第2期》：圆2");
		tmpMap.put("href", "http://120.198.235.230/ugcyd.qq.com/o0552smunqq.mp4");
		listTmps.add(tmpMap);
		tmpMap = new HashMap<String, String>();
		tmpMap.put("text", "《高三数学60讲第2期》：椭圆1");
		tmpMap.put("href", "http://120.198.235.230/ugcyd.qq.com/h0553plpp7x.mp4");
		listTmps.add(tmpMap);
		tmpMap = new HashMap<String, String>();
		tmpMap.put("text", "《高三数学60讲第2期》：椭圆2");
		tmpMap.put("href", "http://120.198.235.230/ugcyd.qq.com/m0554zhui6j.mp4");
		listTmps.add(tmpMap);
		tmpMap = new HashMap<String, String>();
		tmpMap.put("text", "《高三数学60讲第2期》：椭圆3");
		tmpMap.put("href", "http://120.198.235.230/ugcyd.qq.com/x0555hbpg3a.mp4");
		listTmps.add(tmpMap);
		tmpMap = new HashMap<String, String>();
		tmpMap.put("text", "《高三数学60讲第2期》：椭圆4");
		tmpMap.put("href", "http://120.198.235.230/ugcyd.qq.com/e0555he466x.mp4");
		listTmps.add(tmpMap);
		tmpMap = new HashMap<String, String>();
		tmpMap.put("text", "《高三数学60讲第2期》：椭圆5");
		tmpMap.put("href", "http://120.198.235.230/ugcyd.qq.com/w05583fe6ny.mp4");
		listTmps.add(tmpMap);
		tmpMap = new HashMap<String, String>();
		tmpMap.put("text", "《高三数学60讲第2期》：椭圆6");
		tmpMap.put("href", "http://120.198.235.230/ugcyd.qq.com/d0558ak2apz.mp4");
		listTmps.add(tmpMap);
		tmpMap = new HashMap<String, String>();
		tmpMap.put("text", "《高三数学60讲第2期》：双曲线1");
		tmpMap.put("href", "http://120.198.235.230/ugcyd.qq.com/y0558nz7z6w.mp4");
		listTmps.add(tmpMap);
		tmpMap = new HashMap<String, String>();
		tmpMap.put("text", "《高三数学60讲第2期》：双曲线2");
		tmpMap.put("href", "http://120.198.235.230/ugcyd.qq.com/d05597bpz74.mp4");
		listTmps.add(tmpMap);
		tmpMap = new HashMap<String, String>();
		tmpMap.put("text", "《高三数学60讲第2期》：双曲线3");
		tmpMap.put("href", "http://120.198.235.230/ugcyd.qq.com/q0560xon8iv.mp4");
		listTmps.add(tmpMap);
		tmpMap = new HashMap<String, String>();
		tmpMap.put("text", "《高三数学60讲第2期》：双曲线4");
		tmpMap.put("href", "http://120.198.235.230/ugcyd.qq.com/n0561i5xgqy.mp4");
		listTmps.add(tmpMap);
		tmpMap = new HashMap<String, String>();
		tmpMap.put("text", "《高三数学60讲第2期》：双曲线5");
		tmpMap.put("href", "http://120.198.235.230/ugcyd.qq.com/k05625twogl.mp4");
		listTmps.add(tmpMap);
		tmpMap = new HashMap<String, String>();
		tmpMap.put("text", "《高三数学60讲第2期》：抛物线1");
		tmpMap.put("href", "http://120.198.235.230/ugcyd.qq.com/d0562s4q460.mp4");
		listTmps.add(tmpMap);
		tmpMap = new HashMap<String, String>();
		tmpMap.put("text", "《高三数学60讲第2期》：抛物线2");
		tmpMap.put("href", "http://120.198.235.230/ugcyd.qq.com/c05630oc3qv.mp4");
		listTmps.add(tmpMap);
		tmpMap = new HashMap<String, String>();
		tmpMap.put("text", "《高三数学60讲第2期》：抛物线3");
		tmpMap.put("href", "http://120.198.235.230/ugcyd.qq.com/w056479rh3d.mp4");
		listTmps.add(tmpMap);
		tmpMap = new HashMap<String, String>();
		tmpMap.put("text", "《高三数学60讲第2期》：抛物线4");
		tmpMap.put("href", "http://120.198.235.230/ugcyd.qq.com/s05660a114p.mp4");
		listTmps.add(tmpMap);
		tmpMap = new HashMap<String, String>();
		tmpMap.put("text", "美提课堂高一物理直播课：摩擦角的概念&《高三数学60讲第2期》：数列1");
		tmpMap.put("href", "http://120.198.235.230/ugcyd.qq.com/g0567f3mr3k.mp4");
		listTmps.add(tmpMap);
		tmpMap = new HashMap<String, String>();
		tmpMap.put("text", "《高三数学60讲第2期》：数列2");
		tmpMap.put("href", "http://120.198.235.230/ugcyd.qq.com/d05684l2tam.mp4");
		listTmps.add(tmpMap);
		tmpMap = new HashMap<String, String>();
		tmpMap.put("text", "《高三数学60讲第2期》：数列3");
		tmpMap.put("href", "http://120.198.235.230/ugcyd.qq.com/a0500hexjn2.mp4");
		listTmps.add(tmpMap);
		tmpMap = new HashMap<String, String>();
		tmpMap.put("text", "《高三数学60讲第2期》：数列4");
		tmpMap.put("href", "http://120.198.235.230/ugcyd.qq.com/u0501u6sw0u.mp4");
		listTmps.add(tmpMap);
		tmpMap = new HashMap<String, String>();
		tmpMap.put("text", "《高三数学60讲第2期》：数列5");
		tmpMap.put("href", "http://120.198.235.230/ugcyd.qq.com/o050235vkyj.mp4");
		listTmps.add(tmpMap);
		tmpMap = new HashMap<String, String>();
		tmpMap.put("text", "《高三数学60讲第2期》：数列6");
		tmpMap.put("href", "http://120.198.235.230/ugcyd.qq.com/f0504sf29ma.mp4");
		listTmps.add(tmpMap);
		tmpMap = new HashMap<String, String>();
		tmpMap.put("text", "《高三数学60讲第2期》：数列7");
		tmpMap.put("href", "http://120.198.235.230/ugcyd.qq.com/j05051osmte.mp4");
		listTmps.add(tmpMap);
		tmpMap = new HashMap<String, String>();
		tmpMap.put("text", "《高三数学60讲第2期》：数列8");
		tmpMap.put("href", "http://120.198.235.230/ugcyd.qq.com/f0507w1tax1.mp4");
		listTmps.add(tmpMap);
		tmpMap = new HashMap<String, String>();
		tmpMap.put("text", "《高三数学60讲第2期》：排列组合1");
		tmpMap.put("href", "http://120.198.235.230/ugcyd.qq.com/e0508deejbq.mp4");
		listTmps.add(tmpMap);
		tmpMap = new HashMap<String, String>();
		tmpMap.put("text", "《高三数学60讲第2期》：排列组合2");
		tmpMap.put("href", "http://120.198.235.230/ugcyd.qq.com/c0510yvmoqh.mp4");
		listTmps.add(tmpMap);
		tmpMap = new HashMap<String, String>();
		tmpMap.put("text", "《高三数学60讲第2期》：排列组合3");
		tmpMap.put("href", "http://120.198.235.230/ugcyd.qq.com/y051178d158.mp4");
		listTmps.add(tmpMap);
		tmpMap = new HashMap<String, String>();
		tmpMap.put("text", "《高三数学60讲第2期》：排列组合4");
		tmpMap.put("href", "http://120.198.235.230/ugcyd.qq.com/l0513jbpsd7.mp4");
		listTmps.add(tmpMap);
		tmpMap = new HashMap<String, String>();
		tmpMap.put("text", "《高三数学60讲第2期》：排列组合5");
		tmpMap.put("href", "http://120.198.235.230/ugcyd.qq.com/g0515h8scy3.mp4");
		listTmps.add(tmpMap);
		tmpMap = new HashMap<String, String>();
		tmpMap.put("text", "《高三数学60讲第2期》：排列组合6");
		tmpMap.put("href", "http://120.198.235.230/ugcyd.qq.com/d0517a4xayo.mp4");
		listTmps.add(tmpMap);
		tmpMap = new HashMap<String, String>();
		tmpMap.put("text", "《高三数学60讲第2期》：二项式定理 杨振宁二三事");
		tmpMap.put("href", "http://120.198.235.230/ugcyd.qq.com/x0518ltzgka.mp4");
		listTmps.add(tmpMap);
		tmpMap = new HashMap<String, String>();
		tmpMap.put("text", "《高三数学60讲第2期》：概率1 双色球中500万的概率是多大？");
		tmpMap.put("href", "http://120.198.235.230/ugcyd.qq.com/f0520vaw5rr.mp4");
		listTmps.add(tmpMap);
		tmpMap = new HashMap<String, String>();
		tmpMap.put("text", "《高三数学60讲第2期》：概率2");
		tmpMap.put("href", "http://120.198.235.230/ugcyd.qq.com/z0522h13j78.mp4");
		listTmps.add(tmpMap);
		tmpMap = new HashMap<String, String>();
		tmpMap.put("text", "《高三数学60讲第2期》：立体几何1");
		tmpMap.put("href", "http://120.198.235.230/ugcyd.qq.com/f0523nvi371.mp4");
		listTmps.add(tmpMap);
		tmpMap = new HashMap<String, String>();
		tmpMap.put("text", "《高三数学60讲第2期》：立体几何2");
		tmpMap.put("href", "http://120.198.235.230/ugcyd.qq.com/a0525wmd2mq.mp4");
		listTmps.add(tmpMap);
		tmpMap = new HashMap<String, String>();
		tmpMap.put("text", "《高三数学60讲第2期》：立体几何3");
		tmpMap.put("href", "http://120.198.235.230/ugcyd.qq.com/z0525080htx.mp4");
		listTmps.add(tmpMap);
		tmpMap = new HashMap<String, String>();
		tmpMap.put("text", "《高三数学60讲第2期》：立体几何4");
		tmpMap.put("href", "http://120.198.235.230/ugcyd.qq.com/o05268gmu57.mp4");
		listTmps.add(tmpMap);
		tmpMap = new HashMap<String, String>();
		tmpMap.put("text", "《高三数学60讲第2期》：立体几何5");
		tmpMap.put("href", "http://120.198.235.230/ugcyd.qq.com/y0526xwd3rm.mp4");
		listTmps.add(tmpMap);
		tmpMap = new HashMap<String, String>();
		tmpMap.put("text", "《高三数学60讲第2期》：立体几何6");
		tmpMap.put("href", "http://120.198.235.230/ugcyd.qq.com/w0528mh5nn4.mp4");
		listTmps.add(tmpMap);
		tmpMap = new HashMap<String, String>();
		tmpMap.put("text", "《高三数学60讲第2期》：立体几何7");
		tmpMap.put("href", "http://120.198.235.230/ugcyd.qq.com/t05282ntedp.mp4");
		listTmps.add(tmpMap);
		tmpMap = new HashMap<String, String>();
		tmpMap.put("text", "《高三数学60讲第2期》：立体几何8");
		tmpMap.put("href", "http://120.198.235.230/ugcyd.qq.com/n0529wjf03e.mp4");
		listTmps.add(tmpMap);
		tmpMap = new HashMap<String, String>();
		tmpMap.put("text", "《高三数学60讲第2期》：立体几何9 人人都要懂的经纬度知识");
		tmpMap.put("href", "http://120.198.235.230/ugcyd.qq.com/a0530vzfx1v.mp4");
		listTmps.add(tmpMap);
		tmpMap = new HashMap<String, String>();
		tmpMap.put("text", "《高三数学60讲第2期》：立体几何10");
		tmpMap.put("href", "http://120.198.235.230/ugcyd.qq.com/l0531mkyc6v.mp4");
		listTmps.add(tmpMap);
		tmpMap = new HashMap<String, String>();
		tmpMap.put("text", "《高三数学60讲第2期》：立体几何11");
		tmpMap.put("href", "http://120.198.235.230/ugcyd.qq.com/n0533aym909.mp4");
		listTmps.add(tmpMap);
		tmpMap = new HashMap<String, String>();
		tmpMap.put("text", "《高三数学60讲第2期》：立体几何12");
		tmpMap.put("href", "http://120.198.235.230/ugcyd.qq.com/q0534gxlyx3.mp4");
		listTmps.add(tmpMap);
		tmpMap = new HashMap<String, String>();
		tmpMap.put("text", "《高三数学60讲第2期》：立体几何13");
		tmpMap.put("href", "http://120.198.235.230/ugcyd.qq.com/f0534oxonfb.mp4");
		listTmps.add(tmpMap);
		tmpMap = new HashMap<String, String>();
		tmpMap.put("text", "《高三数学60讲第2期》：立体几何14");
		tmpMap.put("href", "http://120.198.235.230/ugcyd.qq.com/v0536jz0nmx.mp4");
		listTmps.add(tmpMap);
		tmpMap = new HashMap<String, String>();
		tmpMap.put("text", "清华学霸老师开讲：赌场里的那些套路");
		tmpMap.put("href", "http://120.198.235.230/ugcyd.qq.com/h03999of9y4.mp4");
		listTmps.add(tmpMap);
		tmpMap = new HashMap<String, String>();
		tmpMap.put("text", "李永乐公开课：据说这是一个影响全国彩票销量的短视频");
		tmpMap.put("href", "http://120.198.235.230/ugcyd.qq.com/m05118ew3vl.mp4");
		listTmps.add(tmpMap);
		tmpMap = new HashMap<String, String>();
		tmpMap.put("text", "李永乐视频课：建一个机场 到周边三个城市距离之和最小的点怎么找");
		tmpMap.put("href", "http://120.198.235.230/ugcyd.qq.com/d0514wdrj2b.mp4");
		listTmps.add(tmpMap);
	    return listTmps;
	}

	
	public static void download3(String localFileName,String fileName) {
        FileOutputStream out = null;
        InputStream in = null;
        
        try{
            URL url = new URL(fileName);
            URLConnection urlConnection = url.openConnection();
            HttpURLConnection httpURLConnection = (HttpURLConnection) urlConnection;
            
            // true -- will setting parameters
            httpURLConnection.setDoOutput(true);
            // true--will allow read in from
            httpURLConnection.setDoInput(true);
            // will not use caches
            httpURLConnection.setUseCaches(false);
            // setting serialized
            httpURLConnection.setRequestProperty("Content-type", "application/x-java-serialized-object");
            // default is GET                        
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setRequestProperty("connection", "Keep-Alive");
            httpURLConnection.setRequestProperty("Charsert", "UTF-8");
            // 1 min
            httpURLConnection.setConnectTimeout(60000);
            // 1 min
            httpURLConnection.setReadTimeout(60000);

           // httpURLConnection.addRequestProperty("fileName", remoteFileName);

            // connect to server (tcp)
            httpURLConnection.connect();

            in = httpURLConnection.getInputStream();// send request to
                                                                // server
            File file = new File(localFileName);
            if(!file.exists()){
                file.createNewFile();
            }

            out = new FileOutputStream(file);  
            byte[] buffer = new byte[4096];
            int readLength = 0;
            while ((readLength=in.read(buffer)) > 0) {
                byte[] bytes = new byte[readLength];
                System.arraycopy(buffer, 0, bytes, 0, readLength);
                out.write(bytes);
            }
            
            out.flush();
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            try {
                if(in != null){
                    in.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            
            try {
                if(out != null){
                    out.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}