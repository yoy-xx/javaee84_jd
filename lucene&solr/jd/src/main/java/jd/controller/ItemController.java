package jd.controller;

import jd.pojo.ResultModel;
import jd.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("item")
public class ItemController {

    @Autowired
    private ItemService itemService;

    /**
     * 需求: 根据关键词查询索引库商品数据
     * String queryString,
     * String catalog_name,
     * String price,
     * String sort,
     * Integer page,
     * Integer rows
     * @return ResultModel
     * 业务: 接受参数,数据回显
     */

    @RequestMapping("list")
    public String showList(String queryString,
                           String catalog_name,
                           String price,
                           @RequestParam(defaultValue = "1") String sort,
                           @RequestParam(defaultValue = "1") Integer page,
                           @RequestParam(defaultValue = "30") Integer rows,
                           Model model){

        ResultModel rModel= itemService.queryProduct(queryString, catalog_name, price, sort, page, rows);

        model.addAttribute("queryString",queryString);
        model.addAttribute("catalog_name",catalog_name);
        model.addAttribute("price",price);
        model.addAttribute("result",rModel);
        model.addAttribute("sort",sort);

        return "product_list";
    }

}
