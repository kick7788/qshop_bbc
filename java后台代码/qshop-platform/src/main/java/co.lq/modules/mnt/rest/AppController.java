package co.lq.modules.mnt.rest;

import java.io.IOException;
import java.util.Set;

import javax.servlet.http.HttpServletResponse;

import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.lq.aop.log.Log;
import co.lq.modules.mnt.domain.App;
import co.lq.modules.mnt.service.AppService;
import co.lq.modules.mnt.service.dto.AppQueryCriteria;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @author zhanghouying
 * @date 2019-08-24
 */
@Api(tags = "应用管理")
@RestController
@RequestMapping("/api/app")
public class AppController {

    private final AppService appService;

    public AppController(AppService appService) {
        this.appService = appService;
    }

    @Log("导出应用数据")
    @ApiOperation("导出应用数据")
    @GetMapping(value = "/download")
    @PreAuthorize("@el.check('admin','app:list')")
    public void download(HttpServletResponse response, AppQueryCriteria criteria) throws IOException {
        appService.download(appService.queryAll(criteria), response);
    }

    @Log("查询应用")
    @ApiOperation(value = "查询应用")
    @GetMapping
    @PreAuthorize("@el.check('admin','app:list')")
    public ResponseEntity<Object> getApps(AppQueryCriteria criteria, Pageable pageable) {
        return new ResponseEntity<>(appService.queryAll(criteria, pageable), HttpStatus.OK);
    }

    @Log("新增应用")
    @ApiOperation(value = "新增应用")
    @PostMapping
    @PreAuthorize("@el.check('admin','app:add')")
    public ResponseEntity<Object> create(@Validated @RequestBody App resources) {
        return new ResponseEntity<>(appService.create(resources), HttpStatus.CREATED);
    }

    @Log("修改应用")
    @ApiOperation(value = "修改应用")
    @PutMapping
    @PreAuthorize("@el.check('admin','app:edit')")
    public ResponseEntity<Object> update(@Validated @RequestBody App resources) {
        appService.update(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Log("删除应用")
    @ApiOperation(value = "删除应用")
    @DeleteMapping
    @PreAuthorize("@el.check('admin','app:del')")
    public ResponseEntity<Object> delete(@RequestBody Set<Long> ids) {
        appService.delete(ids);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
