import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.gox.GoxApplication;
import com.gox.basic.domain.ArchivalCodeNum;
import com.gox.basic.domain.FormJson;
import com.gox.basic.domain.Metadata;
import com.gox.basic.domain.form.FormDesignerData;
import com.gox.basic.domain.form.Props;
import com.gox.basic.domain.vo.TableFieldVo;
import com.gox.basic.mapper.ArchivalCodeSettingMapper;
import com.gox.basic.service.*;
import com.gox.basic.utils.ExportUtil;
import com.gox.common.plugin.AutoId;
import com.gox.common.utils.spring.PropertiesUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.*;

/**
 * @author Naruto
 * @Description: a
 * @date 2021-02-2119:40
 * @Version: 1.0
 */
@SpringBootTest(classes = GoxApplication.class)
public class AppTest {
    @Autowired
    private IMetadataService metadataService;
    @Autowired
    private IFieldsItemService fieldsItemService;
    @Test
    public void test() throws Exception {
        Selector selector = Selector.open();
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.configureBlocking(false);
        serverSocketChannel.bind(new InetSocketAddress(9881));
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        while (selector.select()>0){
            Iterator<SelectionKey> selectionKeys = selector.selectedKeys().iterator();
            while (selectionKeys.hasNext()){
                SelectionKey selectionKey = selectionKeys.next();
                if (selectionKey.isAcceptable()){
                    SocketChannel socketChannel = serverSocketChannel.accept();
                    socketChannel.configureBlocking(false);
                    socketChannel.register(selector,SelectionKey.OP_READ);
                }
                else if(selectionKey.isReadable()){
                    SocketChannel socketChannel = (SocketChannel) selectionKey.channel();
                    ByteBuffer buffer = ByteBuffer.allocate(1024);
                    int length = 0;
                    while ((length=socketChannel.read(buffer))!=0){

                    }
                }
            }
        }
    }
    @Test
    public void te1() throws Throwable {
        Metadata metadata = new Metadata();
        metadata.setNodeId(2359089195122688L);
        metadata.setDeptId(103L);
        metadata.setParentId(0L);
        List<Metadata> list = metadataService.selectMetadataList(metadata);
        List<Metadata> bigList = new ArrayList<>();
        for (int i = 0; i < 10000; i++) {
            bigList.addAll(list);
        }
        List<TableFieldVo> l2 = fieldsItemService.selectTableFieldByNodeIdAndDeptId(2359089195122688L, 103L);
        ExportUtil.exportExcel(bigList,l2,"aaaaa.xlsx");

    }
    @Test
    void t(){
        System.out.println(PropertiesUtils.profile);
    }
}
