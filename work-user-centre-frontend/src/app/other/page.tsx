// ./src/app/other/page.tsx: 其他页面

"use client";

import "./page.css";
import MdViewer from "@/components/MdViewer";
import {Card, Col, Row, Space} from "antd";
import React from "react";

export default function Other() {
    return (
        <div id="otherPage" style={{width: "100%", margin: "0 auto"}}>
            <Row gutter={[{ xs: 8, sm: 16, md: 24, lg: 32 }, { xs: 8, sm: 16, md: 24, lg: 32 }]}>
                <Col span={12}>
                    <Card title="项目简介" variant="borderless" hoverable={true} style={{width: "100%", height: "100%"}}>
                        <MdViewer value={`这是一个用户中心项目，用于管理用户信息，并且作为微服务的一环存在于工作室的微服务架构中。`}/>
                    </Card>
                </Col>
                <Col span={12}>
                    <Card title="项目特征" variant="borderless" hoverable={true} style={{width: "100%", height: "100%"}}>
                        <MdViewer value={`开放的 \`OpenAPI\` 接口文档让您不再需要编写复杂的登陆逻辑。`}/>
                    </Card>
                </Col>
                <Col span={12}>
                    <Card title="用户友好" variant="borderless" hoverable={true} style={{width: "100%", height: "100%"}}>
                        <MdViewer value={`支持用户查看自己的登陆数据，并且借用该服务进行单点登陆。`}/>
                    </Card>
                </Col>
                <Col span={12}>
                    <Card title="管理友好" variant="borderless" hoverable={true} style={{width: "100%", height: "100%"}}>
                        <MdViewer value={`支持管理查看自己的登陆服务，并且借用该服务进行用户操作。`}/>
                    </Card>
                </Col>
            </Row>
        </div>
    );
}
