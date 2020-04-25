package com.mwj.personweb;

/** @Auther: munjie @Date: 4/12/2020 23:49 @Description: */
import java.util.ArrayList;
import java.util.List;

/**
 * 判断一个字符串，是公司名字，还是人名字
 *
 * @author YQ
 */
public class TestUtil {
  public static boolean entValidate(String entName) {

    // 穷举判断条件
    List<String> listOne = new ArrayList<>();
    List<String> listTwo = new ArrayList<>();
    List<String> listThree = new ArrayList<>();
    List<String> listException1 = new ArrayList<>();
    List<String> listException2 = new ArrayList<>();
    List<String> listGuFen = new ArrayList<>();

    listOne.add("有限");
    listOne.add("无限");
    listOne.add("集团");
    listOne.add("合作");
    listOne.add("会社");
    listOne.add("合伙");
    listOne.add("企业");

    listTwo.add("厂");
    listTwo.add("公司");
    listTwo.add("行");
    listTwo.add("社");
    listTwo.add("中心");
    listTwo.add("网吧");
    listTwo.add("院");
    listTwo.add("部");
    listTwo.add("处");
    listTwo.add("股");
    listTwo.add("队");
    listTwo.add("委员会");
    listTwo.add("合作");
    listTwo.add("库");
    listTwo.add("局");
    listTwo.add("村");
    listTwo.add("团");
    listTwo.add("站");
    listTwo.add("店");
    listTwo.add("所");
    listTwo.add("段");
    listTwo.add("厅");
    listTwo.add("组");
    listTwo.add("工作室");
    listTwo.add("研究中心");
    listTwo.add("办公室");
    listTwo.add("商场");
    listTwo.add("大学");

    listThree.add("Co.");
    listThree.add("Limited");
    listThree.add("LIMITED");
    listThree.add("LTD.");
    listThree.add("INC.");
    listThree.add("LLC.");

    listException1.add("委派代表");

    listException2.add("财政部");
    listException2.add("国务院");
    listException2.add("国资委");

    listGuFen.add("国家股");
    listGuFen.add("人股");
    listGuFen.add("流通股");
    listGuFen.add("公众股");
    listGuFen.add("工股");
    listGuFen.add("股东");
    listGuFen.add("A股");
    listGuFen.add("H股");
    listGuFen.add("B股");
    listGuFen.add("上市股");
    listGuFen.add("基本社员 ");
    listGuFen.add("集体股");
    listGuFen.add("合作股");
    listGuFen.add("募集股");

    // 根据字符串判断，是公司 还是 个人
    boolean flag = false;

    if (entName.length() > 4) {
      for (int i = 0; i < listOne.size(); i++) {
        if (entName.contains(listOne.get(i))) {
          flag = true;
          break;
        }
      }

      if (!flag) {
        for (int i = 0; i < listTwo.size(); i++) {
          if (entName.contains(listTwo.get(i))) {
            flag = true;
            break;
          }
        }
      }

      if (!flag) {
        for (int i = 0; i < listThree.size(); i++) {
          if (entName.contains(listThree.get(i))) {
            flag = true;
            break;
          }
        }
      }

      if (!flag) {
        for (int i = 0; i < listException1.size(); i++) {
          if (entName.contains(listException1.get(i))) {
            flag = true;
            break;
          }
        }
      }

      if (!flag) {
        for (int i = 0; i < listGuFen.size(); i++) {
          if (entName.contains(listGuFen.get(i))) {
            flag = true;
            break;
          }
        }
      }
    }

    if (!flag) {
      for (int i = 0; i < listException2.size(); i++) {
        if (entName.contains(listException2.get(i))) {
          flag = true;
          break;
        }
      }
    }

    if (!flag) {
      if (entName.length() == 4 && entName.contains("大学")) {
        flag = true;
      }
    }

    return flag;
  }

  public static void main(String[] args) {
    String str = "上海盛大网络发展有限公司";
    System.out.println("测试公司名称是:" + str);
    boolean flag = TestUtil.entValidate(str);
    System.out.println("检测结果:" + flag);
  }
}
