# Knowledge
## Control Plane
- 🧠 Control Plane quyết định chuyện gì phải chạy ở đâu
- 🦾 Worker Node thực thi
- Control Plane làm gì?
    - Nhận & xử lý lệnh từ kubectl, API client, kube-apiserver 👉 Đây là cổng duy nhất vào cluster
    - Lưu trạng thái cluster (pod, node, config, secret, desired state) 👉 etcd = database sống còn
    - Quyết định pod chạy ở đâu qua kube-scheduler (chọn node phù hợp, dựa vào resource, affinity, taint…)
    - Giữ cluster đúng trạng thái mong muốn: controller-manager (pod die → tạo lại, Node down → reschedule, Replica thiếu → scale lên)
- Các thành phần của Control Plane
    - kube-apiserver: API trung tâm
    - etcd: Lưu trạng thái
    - kube-scheduler: chọn node
    - controller-manager: Giữ desired state
- Control Plane chạy ở đâu?
    - Trên node riêng (prod)
    - Hoặc VM riêng (managed K8s)
    - ❌ Không chạy workload app
- Control Plane KHÔNG làm gì?
    - ❌ Không chạy Pod ứng dụng
    - ❌ Không xử lý request user
    - ❌ Không load balance traffic
- 🧠 So sánh dễ hiểu
    - Control Plane giống bộ não + ban điều hành
    - Worker Node giống công nhân

## Kubelet
- Kubelet ("let" là hậu tố tiếng Anh có nghĩa là nhỏ, đơn vị con như servlet, applet) là agent cốt lõi chạy trên mỗi node trong Kubernetes cluster.
- Control Plane ra lệnh – kubelet là thằng trực tiếp làm
- Nó làm gì? 
    - Kết nối tới kube-apiserver: lấy thông tin pod nào phải chạy trên node này, container image gì, resource bao nhiêu
    - Quản lý container: không tự chạy container mà dùng container runtime qua CRI (Container Runtime Interface)
    - Đảm bảo “desired state”: phát hiện restart container báo cáo trạng thái về control plane
    - Health check & status: chạy liveness / readiness / startup probe
    - Mount volume & network: ConfigMap / Secret / PVC, cấu hình network cho Pod (qua CNI)
- Nằm ở đâu?
    - Chạy trên worker node
    - Mỗi node 1 kubelet
    - Không chạy trong Pod
- Kubelet KHÔNG làm gì?
    - Không schedule Pod
    - Không load balance
    - Không expose service
    - Không quản lý cluster-wide
- 🧠 Ví dụ dễ hiểu
    - Pod giống như “đơn đặt hàng”
    - Scheduler chọn “nhà máy”
    - Kubelet là "quản đốc" nhà máy: đảm bảo đơn được sản xuất đúng yêu cầu

## General Workflow
```
kubectl apply -f app.yaml
        ↓
kube-apiserver (Control Plane)
        ↓
etcd (Control Plane, lưu desired state)
        ↓
scheduler chọn node (Control Plane)
        ↓
kubelet (worker node)
        ↓
container runtime chạy pod
```

# Install kubectl (Windows)
https://kubernetes.io/docs/tasks/tools/install-kubectl-windows/
Run cmd as Admin 
choco install kubernetes-cli
kubectl version --client

# Install Minikube (Windows)
choco install minikube

# Start
minikube start

# Status/Dashboard
minikube status
minikube dashboard -> Will open a web console

# Get nodes/pods
kubectl get nodes
kubectl get pods -A

