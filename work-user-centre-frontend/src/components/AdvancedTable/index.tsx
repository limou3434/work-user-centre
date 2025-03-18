/**
 * AdvancedTable.tsx: 可拖拽排序的高级表格组件
 */

"use client";

import "./index.css";
import type {ProColumns} from "@ant-design/pro-components";
import {DragSortTable} from "@ant-design/pro-components";
import {message} from "antd";
import {useEffect, useState} from "react";

interface AdvancedTableProps<T> {
    data?: T[];
    columns: ProColumns<T>[];
    rowKey: keyof T;
    title?: string;
    onRowClick?: (record: T) => void;
}

export default function AdvancedTable<T extends Record<string, any>>({
                                                                         data = [],
                                                                         columns,
                                                                         rowKey,
                                                                         title = "数据列表",
                                                                         onRowClick = (record) => console.log("点击了:", record),
                                                                     }: AdvancedTableProps<T>) {

    const [dataSource, setDataSource] = useState<T[]>(data);

    useEffect(() => {
        setDataSource(data);
        console.log("更新数据 data", data);
    }, [data]);

    const handleDragSortEnd = (
        beforeIndex: number,
        afterIndex: number,
        newDataSource: T[]
    ) => {
        console.log("排序后的数据", newDataSource);
        setDataSource(newDataSource);
        message.success("修改列表排序成功").then(r => {
        });
    };

    const enhancedColumns = columns.map((col) => ({
        ...col,
        onCell: (record: T) => ({
            onClick: () => onRowClick?.(record),
            style: {cursor: "pointer"},
        }),
    }));

    return (
        <div className="advanced-table">
            <DragSortTable<T>
                headerTitle={title}
                columns={enhancedColumns}
                rowKey={rowKey as string}
                search={false}
                pagination={false}
                dataSource={dataSource}
                dragSortKey="sort"
                onDragSortEnd={handleDragSortEnd}
                scroll={{x: "max-content"}}
            />
        </div>
    );
}
