package com.premiere.controller;

import com.premiere.common.PageResult;
import com.premiere.common.Result;
import com.premiere.common.StatusCode;
import com.premiere.pojo.Label;
import com.premiere.service.LabelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/label")
@CrossOrigin
public class LabelController extends ExceptionController {

    private final LabelService labelService;

    @Autowired
    public LabelController(LabelService labelService) {
        this.labelService = labelService;
    }


    /**
     * 添加
     *
     * @param label 标签信息
     * @return 是否成功
     */
    @RequestMapping(method = RequestMethod.POST)
    public Result add(@RequestBody Label label) {
        labelService.save(label);
        return new Result(true, StatusCode.OK, "添加成功");
    }

    /**
     * 标签全部列表
     *
     * @return Result
     */
    @RequestMapping(method = RequestMethod.GET)
    public Result findAll() {
        return new Result(true, StatusCode.OK, "成功查询到数据", labelService.findAll());
    }

    /**
     * 推荐标签列表
     *
     * @return Result
     */
    @RequestMapping(method = RequestMethod.GET, value = "toplist")
    public Result findTopList() {
        return new Result(true, StatusCode.OK, "成功查询到数据", labelService.findTopList("1"));
    }

    /**
     * 有效标签列表
     *
     * @return Result
     */
    @RequestMapping(method = RequestMethod.GET, value = "list")
    public Result findByStateAllList() {
        return new Result(true, StatusCode.OK, "成功查询到数据", labelService.findByStateAllList("2"));
    }

    /**
     * 根据 ID 查询
     *
     * @param labelId Label 的 ID
     * @return Result
     */
    @RequestMapping(method = RequestMethod.GET, value = "/{labelId}")
    public Result findLabelById(@PathVariable String labelId) {
        return new Result(true, StatusCode.OK, "成功查询到数据", labelService.findLabelById(labelId));
    }


    /**
     * 修改 Label
     *
     * @param labelId Label ID
     * @param label   Label
     * @return Result
     */
    @RequestMapping(method = RequestMethod.PUT, value = "/{labelId}")
    public Result updateLabelById(@PathVariable String labelId, @RequestBody Label label) {
        label.setId(labelId);
        labelService.save(label);
        return new Result(true, StatusCode.OK, "修改成功");
    }


    /**
     * 删除 Label
     *
     * @param labelId Label ID
     * @return Result
     */
    @RequestMapping(method = RequestMethod.DELETE, value = "/{labelId}")
    public Result deleteLabelById(@PathVariable String labelId) {
        labelService.deleteLabelById(labelId);
        return new Result(true, StatusCode.OK, "操作成功");
    }


    /**
     * 分页
     *
     * @param label 分页条件
     * @param page  页数
     * @param size  每页条数
     * @return Result
     */
    @RequestMapping(method = RequestMethod.POST, value = "/search/{page}/{size}")
    public Result search(@RequestBody Label label,
                         @PathVariable Integer page,
                         @PathVariable Integer size) {
        Page<Label> list = labelService.search(label, page, size);
        return new Result(true, StatusCode.OK, "查询成功", new PageResult<>((long) list.getPageable().getPageNumber(), list.getContent()));
    }

    /**
     * 分页
     *
     * @param label 分页条件
     * @return Result
     */
    @RequestMapping(method = RequestMethod.POST, value = "/search")
    public Result searchByLabel(@RequestBody Label label) {
        return new Result(true, StatusCode.OK, "查询成功", labelService.searchByLabel(label));
    }
}

