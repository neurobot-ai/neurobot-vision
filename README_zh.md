# NeuroBot Vision

<div align="center">  
<img src="https://app.neurobot.co/homePageResources/pics/neurobot-logo-b.png" alt="NeuroBot Vision Logo" width="200">  
<p>🔥 开源的在线视觉开发平台，让计算机视觉应用开发更简单 🔥</p>

<div>  
<a href="https://github.com/neurobot-ai/neurobot-vision/stargazers"><img src="https://img.shields.io/github/stars/neurobot-ai/neurobot-vision" alt="Stars Badge"></a>  
<a href="https://github.com/neurobot-ai/neurobot-vision/network/members"><img src="https://img.shields.io/github/forks/neurobot-ai/neurobot-vision" alt="Forks Badge"></a>  
<a href="https://github.com/neurobot-ai/neurobot-vision/issues"><img src="https://img.shields.io/github/issues/neurobot-ai/neurobot-vision" alt="Issues Badge"></a>  
<a href="https://github.com/neurobot-ai/neurobot-vision/blob/main/LICENSE"><img src="https://img.shields.io/github/license/neurobot-ai/neurobot-vision" alt="License Badge"></a>  
</div>

<p>  
<a href="https://www.neurobot.co">官网</a> •  
<a href="https://github.com/neurobot-ai/neurobot-vision/blob/main/README_zh.md#-%E5%8A%9F%E8%83%BD%E7%89%B9%E6%80%A7">功能特性</a> •  
<a href="https://github.com/neurobot-ai/neurobot-vision/blob/main/README_zh.md#-%E5%B9%B3%E5%8F%B0%E4%BC%98%E5%8A%BF">平台优势</a> •  
<a href="https://github.com/neurobot-ai/neurobot-vision/blob/main/README_zh.md#-%E5%BC%80%E6%BA%90%E8%AE%A1%E5%88%92">开源计划</a> •  
<a href="https://github.com/neurobot-ai/neurobot-vision/blob/main/README_zh.md#-%E9%83%A8%E7%BD%B2%E6%8C%87%E5%8D%97">部署指南</a> •  
<a href="https://github.com/neurobot-ai/neurobot-vision/blob/main/README_zh.md#-%E8%B4%A1%E7%8C%AE%E6%8C%87%E5%8D%97">贡献指南</a>  
</p>  
</div>

## 📝 平台介绍

NeuroBot Vision 是一个功能强大的在线视觉开发平台，旨在降低计算机视觉技术的应用门槛，让开发者、研究人员和企业能够快速构建、部署和管理视觉智能应用。

无论是目标检测、图像分割、目标分类还是更复杂的视觉任务，NeuroBot Vision 都能提供直观的界面和强大的后端支持，帮助用户从数据标注、模型训练到应用部署的全流程管理。

平台采用前后端分离架构，前端基于 Vue 构建交互式界面，后端结合 Spring 框架和 Python 机器学习生态，为用户提供高效、稳定且可扩展的视觉开发体验。

## ✨ 功能特性

### 1. 数据管理与标注

- 支持多种图像格式上传与管理
- 提供直观的标注工具，包括矩形框、多边形、关键点等
- 标注进度跟踪与团队协作标注
- 数据版本控制与回溯功能

### 2. 模型训练与管理

- 可视化模型训练流程，支持参数调整与优化
- 预置多种经典视觉模型，开箱即用
- 自定义模型训练与导入功能
- 训练过程实时监控与日志分析
- 模型版本管理与性能对比

### 3. 视觉任务支持

- 目标检测：精确识别图像中的多个目标并定位
- 图像分割：像素级别的目标区域划分
- 目标分类：对图像内容进行分类与识别
- 实时视频流处理：支持摄像头与视频文件的实时分析

### 4. 应用部署与集成

- 一键部署模型为 API 服务
- 支持多种部署方式（云端、边缘设备）
- 提供 SDK 与 API 文档，方便第三方系统集成
- 应用性能监控与日志管理

### 5. 团队协作与权限管理

- 多角色用户管理与权限控制
- 项目共享与协作开发
- 操作日志与审计追踪

## 🌟 平台优势

### 1. Agent一键开发

无需深入掌握复杂的机器学习算法，通过直观的可视化界面即可完成视觉应用开发，大幅降低技术门槛。

### 2. 全流程覆盖

从数据采集、标注、模型训练到应用部署，提供一站式解决方案，简化开发流程，提高工作效率。

### 3. 高性能算法

基于优化的 YOLO 系列算法及其他先进视觉模型，保证检测精度与处理速度的平衡，满足各类场景需求。

### 4. 灵活扩展

模块化设计使平台具备高度可扩展性，支持自定义算法集成和功能扩展，适应不同行业场景。

### 5. 多端适配

支持云端部署与边缘计算，可轻松集成到各类硬件设备中，满足不同环境下的应用需求。

### 6. 开源生态

完全开源的代码 base，允许用户自由定制与二次开发，构建专属视觉解决方案。

## 📅 开源计划

本项目将分阶段逐步开源，以下是详细的开源计划：

1. **Java 公共组件**（已开源）
   
   - 基础工具类
   - 通用服务接口
   - 异常处理机制
   - 日志系统

2. **Java 核心模块**（即将开源）
   
   - 业务逻辑处理
   - 权限管理系统
   - 数据访问层
   - 任务调度系统

3. **SQL 脚本与数据库设计**（2025 年 Q3）
   
   - 数据库表结构
   - 初始化脚本
   - 数据迁移工具
   - 数据库优化方案

4. **前端核心代码**（2025 年 Q3）
   
   - Vue 组件库
   - 状态管理
   - 路由配置
   - 响应式布局实现

5. **YOLO 定位算法**（2025 年 Q4）
   
   - 算法实现
   - 训练脚本
   - 预训练模型
   - 性能评估工具

6. **YOLO 分割算法**（2025 年 Q4）
   
   - 实例分割实现
   - 语义分割支持
   - 模型优化方案
   - 推理加速方法

7. **YOLO 分类算法**（2025 年 Q4）
   
   - 图像分类模型
   - 迁移学习支持
   - 多标签分类
   - 模型压缩方案

## 🚀 部署指南（简明）

只保留最简启动步骤：下载镜像、导入 Docker 并启动容器。更详细的部署教程请参见：

<a href="https://github.com/neurobot-ai/neurobot-vision/blob/main/docs/deploy_docker.md">部署文档</a>

下载并导入镜像：

```bash
wget https://nbai-cloud-3-0.oss-ap-southeast-1.aliyuncs.com/docker-images/neurobot_vision_v1.tar
docker load -i neurobot_vision_v1.tar
```

启动容器（示例，启用 GPU 并映射端口）：

```bash
sudo docker run -idt --privileged=true --restart=always --gpus all --ipc=host -p 38080:8080 \
   --name neurobot_vision neurobot_vision:v1 /app/scripts/start.sh
```

验证容器运行：

```bash
docker ps | grep neurobot_vision
docker logs -f neurobot_vision
```

（说明：详细的系统要求、安装 NVIDIA Container Toolkit 与生产环境配置请见上方文档链接）

## 🤝 贡献指南

我们非常欢迎社区贡献者参与项目的开发与优化！

1. Fork 本仓库
2. 创建特性分支 (`git checkout -b feature/amazing-feature`)
3. 提交更改 (`git commit -m 'Add some amazing feature'`)
4. 推送到分支 (`git push origin feature/amazing-feature`)
5. 打开 Pull Request

请确保你的代码符合项目的编码规范，并通过所有测试。更多贡献细节请参考CONTRIBUTING.md。

## 📄 许可证

本项目采用 Apache License 2.0 许可证，详情请查看许可证文件。

## 📞 联系我们

- 官网: [https://www.neurobot.co](https://www.neurobot.co/)
- 邮箱: contact@neurobot.co
- GitHub: [neurobot-ai (Neurobot) · GitHub](https://github.com/neurobot-ai)

---

<div align="center">  
<p>❤️ 感谢您对 NeuroBot Vision 的关注与支持 ❤️</p>  
<p>如果觉得本项目有价值，请给我们一个 Star 🌟</p>  
</div>
